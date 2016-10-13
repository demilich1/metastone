package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.amazon.AuthorizationRecord;
import com.hiddenswitch.proto3.net.amazon.HashedLoginToken;
import com.hiddenswitch.proto3.net.amazon.UserRecord;
import com.hiddenswitch.proto3.net.models.*;
import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.Instant;
import java.util.Date;
import java.util.regex.Pattern;

public class Accounts extends Service {
	private Pattern usernamePattern = Pattern.compile("[A-Za-z0-9_]+");

	public Accounts() {
		super();
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
		user.emailAddress = request.emailAddress;
		user.name = request.name;
		String userId = save(user);

		LoginToken loginToken = setPassword(userId, request.password);

		response.userId = userId;
		response.loginToken = loginToken;

		return response;
	}

	public LoginResponse login(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		AuthorizationRecord record = getAuthorizationRecord(request.userId);
		if (record == null) {
			response.badUsername = true;
			return response;
		}
		if (!SCryptUtil.check(request.password, record.getScrypt())) {
			response.badPassword = true;
			return response;
		}

		LoginToken token = LoginToken.createSecure();
		HashedLoginToken[] tokens = new HashedLoginToken[Math.max(record.getLoginTokens().length + 1, 5)];
		tokens[0] = new HashedLoginToken(token);

		for (int i = 1; i < record.getLoginTokens().length; i++) {
			tokens[i] = record.getLoginTokens()[i];
		}

		record.setLoginTokens(tokens);
		save(record);

		response.token = token;
		return response;
	}

	public boolean isAuthorizedWithToken(String userId, String token) {
		AuthorizationRecord record = getAuthorizationRecord(userId);

		for (HashedLoginToken loginToken : record.getLoginTokens()) {
			if (SCryptUtil.check(token, loginToken.hashedLoginToken)) {
				return true;
			}
		}

		return false;
	}

	private String save(User user) {
		UserRecord record = new UserRecord();
		record.id = user.name;
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

		record.setScrypt(SCryptUtil.scrypt(password, 16384, 8, 1));

		LoginToken token = LoginToken.createSecure();

		// Invalidates all existing login tokens
		record.setLoginTokens(new HashedLoginToken[]{new HashedLoginToken(token)});
		save(record);

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
		// TODO: Gets the context and recovers the user ID and other authorization information from there
		return "";
	}

	public PlayerProfile getProfileForId(String userId) {
		User user = get(userId);
		PlayerProfile profile = new PlayerProfile();
		profile.name = user.name;
		return profile;
	}

	private Pattern getUsernamePattern() {
		return usernamePattern;
	}
}
