package com.hiddenswitch.proto3.net.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.util.Serialization;

public class ClientConnectionConfigurationConverter implements DynamoDBTypeConverter<String, ClientConnectionConfiguration> {
	@Override
	public String convert(ClientConnectionConfiguration object) {
		return Serialization.serialize(object);
	}

	@Override
	public ClientConnectionConfiguration unconvert(String object) {
		return Serialization.deserialize(object, ClientConnectionConfiguration.class);
	}
}
