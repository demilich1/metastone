package com.hiddenswitch.proto3.net.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.hiddenswitch.proto3.net.util.Serialization;
import net.demilich.metastone.game.decks.Bench;

public class BenchConverter implements DynamoDBTypeConverter<String, Bench> {
	@Override
	public String convert(Bench object) {
		return Serialization.serialize(object);
	}

	@Override
	public Bench unconvert(String object) {
		return Serialization.deserialize(object, Bench.class);
	}
}
