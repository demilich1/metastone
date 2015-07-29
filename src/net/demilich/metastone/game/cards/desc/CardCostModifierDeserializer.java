package net.demilich.metastone.game.cards.desc;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierArg;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierDesc;

public class CardCostModifierDeserializer implements JsonDeserializer<CardCostModifierDesc> {

	@SuppressWarnings("unchecked")
	@Override
	public CardCostModifierDesc deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonObject)) {
			throw new JsonParseException("ManaModifier parser expected an JsonObject but found " + json + " instead");
		}
		JsonObject jsonData = (JsonObject) json;
		String cardCostModifierClassName = CardCostModifier.class.getPackage().getName() + "." + jsonData.get("class").getAsString();
		Class<? extends CardCostModifier> cardCostModifierClass;
		try {
			cardCostModifierClass = (Class<? extends CardCostModifier>) Class.forName(cardCostModifierClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new JsonParseException("ManaModifier parser encountered an invalid class: " + cardCostModifierClassName);
		}
		Map<CardCostModifierArg, Object> arguments = CardCostModifierDesc.build(cardCostModifierClass);
		parseArgument(CardCostModifierArg.TARGET_PLAYER, jsonData, arguments, ParseValueType.TARGET_PLAYER);
		parseArgument(CardCostModifierArg.RACE, jsonData, arguments, ParseValueType.RACE);
		parseArgument(CardCostModifierArg.VALUE, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(CardCostModifierArg.MIN_VALUE, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(CardCostModifierArg.CARD_TYPE, jsonData, arguments, ParseValueType.CARD_TYPE);
		parseArgument(CardCostModifierArg.REQUIRED_ATTRIBUTE, jsonData, arguments, ParseValueType.ATTRIBUTE);
		parseArgument(CardCostModifierArg.EXPIRATION_TRIGGER, jsonData, arguments, ParseValueType.EVENT_TRIGGER);
		parseArgument(CardCostModifierArg.TOGGLE_ON_TRIGGER, jsonData, arguments, ParseValueType.EVENT_TRIGGER);
		parseArgument(CardCostModifierArg.TOGGLE_OFF_TRIGGER, jsonData, arguments, ParseValueType.EVENT_TRIGGER);

		return new CardCostModifierDesc(arguments);
	}

	private void parseArgument(CardCostModifierArg arg, JsonObject jsonData, Map<CardCostModifierArg, Object> arguments,
			ParseValueType valueType) {
		String argName = ParseUtils.toCamelCase(arg.toString());
		if (!jsonData.has(argName)) {
			return;
		}
		Object value = ParseUtils.parse(argName, jsonData, valueType);
		arguments.put(arg, value);
	}

}
