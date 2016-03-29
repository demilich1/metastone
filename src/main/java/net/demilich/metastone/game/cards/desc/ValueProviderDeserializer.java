package net.demilich.metastone.game.cards.desc;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProviderArg;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProviderDesc;

public class ValueProviderDeserializer implements JsonDeserializer<ValueProviderDesc> {

	@SuppressWarnings("unchecked")
	@Override
	public ValueProviderDesc deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonObject)) {
			throw new JsonParseException("ValueProvider parser expected an JsonObject but found " + json + " instead");
		}
		JsonObject jsonData = (JsonObject) json;
		String valueProviderClassName = ValueProvider.class.getPackage().getName() + "." + jsonData.get("class").getAsString();
		Class<? extends ValueProvider> valueProviderClass;
		try {
			valueProviderClass = (Class<? extends ValueProvider>) Class.forName(valueProviderClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new JsonParseException("ValueProvider parser encountered an invalid class: " + valueProviderClassName);
		}
		Map<ValueProviderArg, Object> arguments = ValueProviderDesc.build(valueProviderClass);

		parseArgument(ValueProviderArg.TARGET, jsonData, arguments, ParseValueType.TARGET_REFERENCE);
		parseArgument(ValueProviderArg.PLAYER_ATTRIBUTE, jsonData, arguments, ParseValueType.PLAYER_ATTRIBUTE);
		parseArgument(ValueProviderArg.ATTRIBUTE, jsonData, arguments, ParseValueType.ATTRIBUTE);
		parseArgument(ValueProviderArg.MULTIPLIER, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(ValueProviderArg.VALUE, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(ValueProviderArg.MIN, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(ValueProviderArg.MAX, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(ValueProviderArg.OFFSET, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(ValueProviderArg.IF_TRUE, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(ValueProviderArg.IF_FALSE, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(ValueProviderArg.RACE, jsonData, arguments, ParseValueType.RACE);
		parseArgument(ValueProviderArg.TARGET_PLAYER, jsonData, arguments, ParseValueType.TARGET_PLAYER);
		parseArgument(ValueProviderArg.CONDITION, jsonData, arguments, ParseValueType.CONDITION);
		parseArgument(ValueProviderArg.FILTER, jsonData, arguments, ParseValueType.ENTITY_FILTER);
		parseArgument(ValueProviderArg.OPERATION, jsonData, arguments, ParseValueType.ALGEBRAIC_OPERATION);
		parseArgument(ValueProviderArg.VALUE_1, jsonData, arguments, ParseValueType.VALUE);
		parseArgument(ValueProviderArg.VALUE_2, jsonData, arguments, ParseValueType.VALUE);

		return new ValueProviderDesc(arguments);
	}

	private void parseArgument(ValueProviderArg arg, JsonObject jsonData, Map<ValueProviderArg, Object> arguments,
			ParseValueType valueType) {
		String argName = ParseUtils.toCamelCase(arg.toString());
		if (!jsonData.has(argName)) {
			return;
		}
		Object value = ParseUtils.parse(argName, jsonData, valueType);
		arguments.put(arg, value);
	}

}
