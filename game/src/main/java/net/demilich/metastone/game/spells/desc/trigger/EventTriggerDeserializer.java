package net.demilich.metastone.game.spells.desc.trigger;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.demilich.metastone.game.cards.desc.ParseUtils;
import net.demilich.metastone.game.cards.desc.ParseValueType;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;

public class EventTriggerDeserializer implements JsonDeserializer<EventTriggerDesc> {

	@SuppressWarnings("unchecked")
	@Override
	public EventTriggerDesc deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonObject)) {
			throw new JsonParseException("Trigger parser expected an JsonObject but found " + json + " instead");
		}
		JsonObject jsonData = (JsonObject) json;
		String triggerClassName = GameEventTrigger.class.getPackage().getName() + "." + jsonData.get("class").getAsString();
		Class<? extends GameEventTrigger> triggerClass;
		try {
			triggerClass = (Class<? extends GameEventTrigger>) Class.forName(triggerClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new JsonParseException("Trigger parser encountered an invalid class: " + triggerClassName);
		}
		Map<EventTriggerArg, Object> arguments = EventTriggerDesc.build(triggerClass);
		parseArgument(EventTriggerArg.RACE, jsonData, arguments, ParseValueType.RACE);
		parseArgument(EventTriggerArg.CARD_TYPE, jsonData, arguments, ParseValueType.CARD_TYPE);
		parseArgument(EventTriggerArg.TARGET_PLAYER, jsonData, arguments, ParseValueType.TARGET_PLAYER);
		parseArgument(EventTriggerArg.SOURCE_PLAYER, jsonData, arguments, ParseValueType.TARGET_PLAYER);
		parseArgument(EventTriggerArg.SOURCE_ENTITY_TYPE, jsonData, arguments, ParseValueType.ENTITY_TYPE);
		parseArgument(EventTriggerArg.TARGET_ENTITY_TYPE, jsonData, arguments, ParseValueType.ENTITY_TYPE);
		parseArgument(EventTriggerArg.SOURCE_TYPE, jsonData, arguments, ParseValueType.CARD_TYPE);
		parseArgument(EventTriggerArg.ACTION_TYPE, jsonData, arguments, ParseValueType.ACTION_TYPE);
		parseArgument(EventTriggerArg.HOST_TARGET_TYPE, jsonData, arguments, ParseValueType.TARGET_TYPE);
		parseArgument(EventTriggerArg.REQUIRED_ATTRIBUTE, jsonData, arguments, ParseValueType.ATTRIBUTE);
		parseArgument(EventTriggerArg.QUEUE_CONDITION, jsonData, arguments, ParseValueType.CONDITION);
		parseArgument(EventTriggerArg.FIRE_CONDITION, jsonData, arguments, ParseValueType.CONDITION);
		parseArgument(EventTriggerArg.TARGET, jsonData, arguments, ParseValueType.TARGET_REFERENCE);

		return new EventTriggerDesc(arguments);
	}

	private void parseArgument(EventTriggerArg arg, JsonObject jsonData, Map<EventTriggerArg, Object> arguments, ParseValueType valueType) {
		String argName = ParseUtils.toCamelCase(arg.toString());
		if (!jsonData.has(argName)) {
			return;
		}
		Object value = ParseUtils.parse(argName, jsonData, valueType);
		arguments.put(arg, value);
	}

}
