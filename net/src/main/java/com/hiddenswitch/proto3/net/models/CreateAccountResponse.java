package com.hiddenswitch.proto3.net.models;

import com.hiddenswitch.proto3.net.amazon.LoginToken;

import java.io.Serializable;

public class CreateAccountResponse implements Serializable {
	public LoginToken loginToken;
	public String userId;
	public boolean invalidName;
	public boolean invalidEmailAddress;
	public boolean invalidPassword;
}
