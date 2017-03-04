package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class AwsStack {
	public static void initializeStack(AwsStackConfiguration configuration) {
		if (configuration.database == null) {
			return;
		}

		for (Class t : new Class[]{UserRecord.class, AuthorizationRecord.class}) {
			CreateTableRequest request = configuration.database.generateCreateTableRequest(t);
			request.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
			configuration.dynamoDBClient.createTable(request);
		}
	}
}
