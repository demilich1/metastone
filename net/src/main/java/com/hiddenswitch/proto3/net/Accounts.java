package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.amazon.AuthorizationRecord;
import com.hiddenswitch.proto3.net.amazon.UserRecord;
import com.hiddenswitch.proto3.net.models.*;
import com.lambdaworks.crypto.SCryptUtil;


public class Accounts extends Service {
	public Accounts() {
		super();
	}

	public CreateAccountResponse createAccount(CreateAccountRequest request) {
		return new CreateAccountResponse();
	}

	public User create() {
		return new User();
	}

	private boolean isAuthorizedWithToken(String userId, String hashedToken) {
		AuthorizationRecord record = getDatabase().load(AuthorizationRecord.class, userId);
		for (LoginToken loginToken : record.getLoginTokens()) {
			if (SCryptUtil.check(loginToken.token, hashedToken)) {
				return true;
			}
		}
		return false;
	}

	private void setPassword(String userId, String password) {
		AuthorizationRecord record = getDatabase().load(AuthorizationRecord.class, userId);

		if (record == null) {
			record = new AuthorizationRecord();
		}

		record.setScrypt(SCryptUtil.scrypt(password, 16384, 8, 1));

		// Invalidates all existing login tokens
		record.setLoginTokens(new LoginToken[]{});
		getDatabase().save(record);
	}

	public User get(String id) {
		UserRecord record = getDatabase().load(UserRecord.class, id);
		if (record == null) {
			return null;
		}

		return record.user;
	}

	public String getUserId() {
		// Gets the current user ID for the request.
		return "";
	}

	public PlayerProfile getProfileForId(String userId) {
		return null;
	}
}
