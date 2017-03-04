package com.hiddenswitch.proto3.net.amazon;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.hiddenswitch.proto3.net.util.Serialization;
import net.demilich.metastone.game.GameContext;

public class GameContextConverter implements DynamoDBTypeConverter<String, GameContext> {
	@Override
	public String convert(GameContext object) {
		GameContext gameContext = (GameContext) object;
		return Serialization.serialize(object);
	}

	@Override
	public GameContext unconvert(String object) {
		return Serialization.deserialize((String) object, GameContext.class);
	}
}
