package com.hiddenswitch.proto3.net.models;

import java.io.Serializable;

public class CreateAccountRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	public String name;
	public String emailAddress;
	public String password;
}
