package net.demilich.metastone.game.cards.desc;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.demilich.metastone.game.spells.desc.condition.Condition;
import net.demilich.metastone.game.spells.desc.condition.ConditionArg;
import net.demilich.metastone.game.spells.desc.condition.ConditionDesc;

public class ConditionDeserializer implements JsonDeserializer<ConditionDesc> {

	@SuppressWarnings("unchecked")
	@Override
	public ConditionDesc deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonObject)) {
			throw new JsonParseException("Condition parser expected an JsonObject but found " + json + " instead");
		}
		JsonObject jsonData = (JsonObject) json;
		String conditionClassName = Condition.class.getPackage().getName() + "." + jsonData.get("class").getAsString();
		Class<? extends Condition> conditionClass;
		try {
			conditionClass = (Class<? extends Condition>) Class.forName(conditionClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new JsonParseException("Condition parser encountered an invalid class: " + conditionClassName);
		}
		Map<ConditionArg, Object> arguments = ConditionDesc.build(conditionClass);
		parseArgument(ConditionArg.RACE, jsonData, arguments, ParseValueType.RACE);
		parseArgument(ConditionArg.VALUE, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(ConditionArg.VALUE_PROVIDER, jsonData, arguments, ParseValueType.VALUE_PROVIDER);
		parseArgument(ConditionArg.TARGET_PLAYER, jsonData, arguments, ParseValueType.TARGET_PLAYER);
		parseArgument(ConditionArg.TARGET, jsonData, arguments, ParseValueType.TARGET_REFERENCE);
		parseArgument(ConditionArg.OPERATION, jsonData, arguments, ParseValueType.OPERATION);
		parseArgument(ConditionArg.INVERT, jsonData, arguments, ParseValueType.BOOLEAN);
		parseArgument(ConditionArg.ATTRIBUTE, jsonData, arguments, ParseValueType.ATTRIBUTE);
		parseArgument(ConditionArg.CARD_TYPE, jsonData, arguments, ParseValueType.CARD_TYPE);
		parseArgument(ConditionArg.CONDITIONS, jsonData, arguments, ParseValueType.CONDITION_ARRAY);
		parseArgument(ConditionArg.CARD_ID, jsonData, arguments, ParseValueType.STRING);
		parseArgument(ConditionArg.FILTER, jsonData, arguments, ParseValueType.ENTITY_FILTER);

		return new ConditionDesc(arguments);
	}

	private void parseArgument(ConditionArg arg, JsonObject jsonData, Map<ConditionArg, Object> arguments, ParseValueType valueType) {
		String argName = ParseUtils.toCamelCase(arg.toString());
		if (!jsonData.has(argName)) {
			return;
		}
		Object value = ParseUtils.parse(argName, jsonData, valueType);
		arguments.put(arg, value);
	}

}
