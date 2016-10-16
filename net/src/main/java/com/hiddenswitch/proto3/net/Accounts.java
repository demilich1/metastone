package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.amazon.AuthorizationRecord;
import com.hiddenswitch.proto3.net.amazon.HashedLoginToken;
import com.hiddenswitch.proto3.net.amazon.UserRecord;
import com.hiddenswitch.proto3.net.models.*;
import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

public class Accounts extends Service {
	private Pattern usernamePattern = Pattern.compile("[A-Za-z0-9_]+");
	private String userId;

	public Accounts() {
		super();
	}

	public CreateAccountResponse createAccount(String emailAddress, String password, String username) {
		CreateAccountRequest request = new CreateAccountRequest();
		request.emailAddress = emailAddress;
		request.password = password;
		request.name = username;
		return this.createAccount(request);
	}

	public LoginResponse login(String username, String password) {
		LoginRequest request = new LoginRequest();
		request.password = password;
		request.userId = username;
		return this.login(request);
	}

	public CreateAccountResponse createAccount(CreateAccountRequest request) {
		CreateAccountResponse response = new CreateAccountResponse();

		if (!isValidName(request.name)) {
			response.invalidName = true;
			return response;
		}

		if (!isValidEmailAddress(request.emailAddress)) {
			response.invalidEmailAddress = true;
			return response;
		}

		if (!isValidPassword(request.password)) {
			response.invalidPassword = true;
			return response;
		}

		User user = create();
		user.setEmailAddress(request.emailAddress);
		user.setName(request.name);
		String userId = save(user);

		LoginToken loginToken = setPassword(userId, request.password);

		response.userId = userId;
		response.loginToken = loginToken;

		return response;
	}

	public LoginResponse login(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		boolean valid = true;
		if (request == null) {
			valid = false;
		}
		if (request.userId == null) {
			valid = false;
			response.badUsername = true;
		}
		if (request.password == null) {
			valid = false;
			response.badPassword = true;
		}
		AuthorizationRecord record = getAuthorizationRecord(request.userId);
		if (record == null) {
			valid = false;
			response.badUsername = true;
		}

		if (request.password != null
				&& record != null
				&& !SCryptUtil.check(request.password, record.getScrypt())) {
			valid = false;
			response.badPassword = true;
		}

		LoginToken token = null;
		if (valid) {
			token = loginInternal(request, record);
		}

		if (token == null) {
			setUserId(null);
		} else {
			setUserId(request.userId);
		}

		response.token = token;
		return response;
	}

	private LoginToken loginInternal(LoginRequest request, AuthorizationRecord record) {
		LoginToken token = LoginToken.createSecure();
		HashedLoginToken[] tokens = new HashedLoginToken[Math.max(record.getLoginTokens().size() + 1, 5)];
		tokens[0] = new HashedLoginToken(token);

		for (int i = 1; i < record.getLoginTokens().size(); i++) {
			tokens[i] = record.getLoginTokens().get(i);
		}

		record.setLoginTokens(Arrays.asList(tokens));
		record.setUserId(request.userId);
		save(record);
		return token;
	}

	public boolean isAuthorizedWithToken(String userId, String token) {
		if (token == null
				|| userId == null) {
			return false;
		}
		AuthorizationRecord record = getAuthorizationRecord(userId);
		if (record == null) {
			return false;
		}
		for (HashedLoginToken loginToken : record.getLoginTokens()) {
			if (SCryptUtil.check(token, loginToken.getHashedLoginToken())) {
				return true;
			}
		}

		return false;
	}

	private String save(User user) {
		UserRecord record = new UserRecord();
		record.id = user.getName();
		record.user = user;
		getDatabase().save(record);
		return record.id;
	}

	private boolean isValidPassword(String password) {
		return password != null && password.length() >= 1;
	}

	private boolean isValidEmailAddress(String emailAddress) {
		return EmailValidator.getInstance().isValid(emailAddress);
	}

	private boolean isValidName(String name) {
		return getUsernamePattern().matcher(name).matches()
				&& !isVulgar(name);
	}

	private boolean isVulgar(String name) {
		return false;
	}

	private User create() {
		return new User();
	}

	private LoginToken setPassword(String userId, String password) {
		AuthorizationRecord record = getAuthorizationRecord(userId);

		if (record == null) {
			record = new AuthorizationRecord();
		}

		record.setUserId(userId);
		record.setScrypt(SCryptUtil.scrypt(password, 16384, 8, 1));

		LoginToken token = LoginToken.createSecure();

		// Invalidates all existing login tokens
		record.setLoginTokens(Collections.singletonList(new HashedLoginToken(token)));
		save(record);

		setUserId(userId);

		return token;
	}

	private String save(AuthorizationRecord record) {
		getDatabase().save(record);
		return record.getUserId();
	}

	private AuthorizationRecord getAuthorizationRecord(String userId) {
		return getDatabase().load(AuthorizationRecord.class, userId);
	}

	public User get(String id) {
		UserRecord record = getDatabase().load(UserRecord.class, id);
		if (record == null) {
			return null;
		}

		return record.user;
	}

	public String getUserId() {
		return this.userId;
	}

	public PlayerProfile getProfileForId(String userId) {
		User user = get(userId);
		if (user == null) {
			return null;
		}
		PlayerProfile profile = new PlayerProfile();
		profile.name = user.getName();
		return profile;
	}

	private Pattern getUsernamePattern() {
		return usernamePattern;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
