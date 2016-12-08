package com.hiddenswitch.proto3.net.models;

import java.io.Serializable;

public class CreateAccountRequest implements Serializable {
	public String name;
	public String emailAddress;
	public String password;
}
