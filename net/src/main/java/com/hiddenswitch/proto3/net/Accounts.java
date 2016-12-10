package com.hiddenswitch.proto3.net;

import com.hiddenswitch.proto3.net.models.CreateAccountRequest;
import com.hiddenswitch.proto3.net.models.CreateAccountResponse;
import com.hiddenswitch.proto3.net.amazon.LoginRequest;
import com.hiddenswitch.proto3.net.amazon.LoginResponse;

/**
 * Created by bberman on 12/8/16.
 */
public interface Accounts {
	CreateAccountResponse createAccount(CreateAccountRequest request);

	LoginResponse login(LoginRequest request);
}
