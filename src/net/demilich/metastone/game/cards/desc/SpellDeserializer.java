package net.demilich.metastone.game.cards.desc;

import java.lang.reflect.Type;
import java.util.Map;

import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class SpellDeserializer implements JsonDeserializer<SpellDesc> {

	@SuppressWarnings("unchecked")
	@Override
	public SpellDesc deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonObject)) {
			throw new JsonParseException("SpellDesc parser expected an JsonObject but found " + json + " instead");
		}
		JsonObject jsonData = (JsonObject) json;
		String spellClassName = Spell.class.getPackage().getName() + "." + jsonData.get("class").getAsString();
		Class<? extends Spell> spellClass;
		try {
			spellClass = (Class<? extends Spell>) Class.forName(spellClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new JsonParseException("SpellDesc parser encountered an invalid spell class: " + spellClassName);
		}
		Map<SpellArg, Object> spellArgs = SpellDesc.build(spellClass);
		parseArgument(SpellArg.ATTACK_BONUS, jsonData, spellArgs, ParseValueType.INTEGER);
		parseArgument(SpellArg.ARMOR_BONUS, jsonData, spellArgs, ParseValueType.INTEGER);
		parseArgument(SpellArg.HP_BONUS, jsonData, spellArgs, ParseValueType.INTEGER);
		parseArgument(SpellArg.MANA_MODIFIER, jsonData, spellArgs, ParseValueType.INTEGER);
		parseArgument(SpellArg.TARGET, jsonData, spellArgs, ParseValueType.TARGET_REFERENCE);
		parseArgument(SpellArg.TARGET_PLAYER, jsonData, spellArgs, ParseValueType.TARGET_PLAYER);
		parseArgument(SpellArg.VALUE, jsonData, spellArgs, ParseValueType.INTEGER);
		parseArgument(SpellArg.HOW_MANY, jsonData, spellArgs, ParseValueType.INTEGER);
		parseArgument(SpellArg.SECONDARY_VALUE, jsonData, spellArgs, ParseValueType.INTEGER);
		parseArgument(SpellArg.SPELL_1, jsonData, spellArgs, ParseValueType.SPELL);
		parseArgument(SpellArg.SPELL_2, jsonData, spellArgs, ParseValueType.SPELL);
		parseArgument(SpellArg.SPELL_3, jsonData, spellArgs, ParseValueType.SPELL);
		parseArgument(SpellArg.CARD, jsonData, spellArgs, ParseValueType.STRING);
		parseArgument(SpellArg.CARDS, jsonData, spellArgs, ParseValueType.STRING_ARRAY);
		parseArgument(SpellArg.ATTRIBUTE, jsonData, spellArgs, ParseValueType.ATTRIBUTE);
		parseArgument(SpellArg.RANDOM_TARGET, jsonData, spellArgs, ParseValueType.BOOLEAN);
		parseArgument(SpellArg.VALUE_PROVIDER, jsonData, spellArgs, ParseValueType.VALUE_PROVIDER);
		parseArgument(SpellArg.ATTACK_VALUE_PROVIDER, jsonData, spellArgs, ParseValueType.VALUE_PROVIDER);
		parseArgument(SpellArg.HP_VALUE_PROVIDER, jsonData, spellArgs, ParseValueType.VALUE_PROVIDER);
		parseArgument(SpellArg.FILTER, jsonData, spellArgs, ParseValueType.ENTITY_FILTER);
		parseArgument(SpellArg.CARD_FILTER, jsonData, spellArgs, ParseValueType.ENTITY_FILTER);
		parseArgument(SpellArg.RACE, jsonData, spellArgs, ParseValueType.RACE);
		parseArgument(SpellArg.CARD_LOCATION, jsonData, spellArgs, ParseValueType.CARD_LOCATION);
		parseArgument(SpellArg.BOARD_POSITION_RELATIVE, jsonData, spellArgs, ParseValueType.BOARD_POSITION_RELATIVE);
		parseArgument(SpellArg.EXCLUSIVE, jsonData, spellArgs, ParseValueType.BOOLEAN);
		parseArgument(SpellArg.CONDITION, jsonData, spellArgs, ParseValueType.CONDITION);
		parseArgument(SpellArg.CARD_COST_MODIFIER, jsonData, spellArgs, ParseValueType.CARD_COST_MODIFIER);
		parseArgument(SpellArg.TRIGGER, jsonData, spellArgs, ParseValueType.TRIGGER);
		parseArgument(SpellArg.REVERT_TRIGGER, jsonData, spellArgs, ParseValueType.EVENT_TRIGGER);
		parseArgument(SpellArg.SECOND_REVERT_TRIGGER, jsonData, spellArgs, ParseValueType.EVENT_TRIGGER);
		
		return new SpellDesc(spellArgs);
	}

	private void parseArgument(SpellArg spellArg, JsonObject jsonData, Map<SpellArg, Object> spellArgs, ParseValueType valueType) {
		String argName = ParseUtils.toCamelCase(spellArg.toString());
		System.out.println("ArgName: " + argName);
		if (!jsonData.has(argName)) {
			return;
		}
		Object value = ParseUtils.parse(argName, jsonData, valueType);
		
		System.out.println("Setting spellArg " + spellArg + " to "  +value);
		spellArgs.put(spellArg, value);
	}

}
