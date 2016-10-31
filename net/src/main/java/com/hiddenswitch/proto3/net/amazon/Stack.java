package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.hiddenswitch.proto3.net.Games;

public class Stack {

	public static void initializeStack(StackConfiguration configuration) {
		for (Class t : new Class[]{UserRecord.class, AuthorizationRecord.class, GameRecord.class}) {
			CreateTableRequest request = configuration.database.generateCreateTableRequest(t);
			request.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
			configuration.dynamoDBClient.createTable(request);
		}
		configuration.queue.createQueue(Games.MATCHMAKING_QUEUE);
	}
}
