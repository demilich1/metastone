package com.hiddenswitch.proto3.net.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.util.Serialization;

public class ClientConnectionConfigurationConverter implements DynamoDBTypeConverter {
	@Override
	public Object convert(Object object) {
		return Serialization.serialize(object);
	}

	@Override
	public Object unconvert(Object object) {
		return Serialization.deserialize((String) object, ClientConnectionConfiguration.class);
	}
}
