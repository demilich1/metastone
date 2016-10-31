package com.hiddenswitch.proto3.net.util;

import com.google.gson.*;
import net.demilich.metastone.game.targeting.EntityReference;

import java.lang.reflect.Type;

/**
 * Created by bberman on 10/10/16.
 */
public class EntityReferenceSerializer implements JsonSerializer<EntityReference>, JsonDeserializer<EntityReference> {
	@Override
	public EntityReference deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return new EntityReference(json.getAsInt());
	}

	@Override
	public JsonElement serialize(EntityReference src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.getId());
	}
}
