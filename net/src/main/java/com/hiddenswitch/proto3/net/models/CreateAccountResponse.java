package com.hiddenswitch.proto3.net.models;

public class CreateAccountResponse {
	public LoginToken loginToken;
	public String userId;
	public boolean invalidName;
	public boolean invalidEmailAddress;
	public boolean invalidPassword;
}
