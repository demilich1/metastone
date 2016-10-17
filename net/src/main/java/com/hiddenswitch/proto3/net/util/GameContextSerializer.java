package com.hiddenswitch.proto3.net.util;

import com.google.gson.*;
import com.hiddenswitch.proto3.net.models.Game;
import net.demilich.metastone.game.GameContext;

import java.lang.reflect.Type;

public class GameContextSerializer implements JsonSerializer<GameContext>, JsonDeserializer<GameContext> {

	@Override
	public GameContext deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return null;
	}

	@Override
	public JsonElement serialize(GameContext src, Type typeOfSrc, JsonSerializationContext context) {
		if (src == null) {

		}
		JsonElement element = context.serialize(src);
	}
}
