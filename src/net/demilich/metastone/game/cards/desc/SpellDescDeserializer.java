package net.demilich.metastone.game.cards.desc;

import java.lang.reflect.Type;
import java.util.Map;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class SpellDescDeserializer implements JsonDeserializer<SpellDesc> {

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
		parseArgument(SpellArg.ATTACK_BONUS, jsonData, spellArgs, SpellValueType.INTEGER);
		parseArgument(SpellArg.ARMOR_BONUS, jsonData, spellArgs, SpellValueType.INTEGER);
		parseArgument(SpellArg.HP_BONUS, jsonData, spellArgs, SpellValueType.INTEGER);
		parseArgument(SpellArg.TARGET, jsonData, spellArgs, SpellValueType.TARGET_REFERENCE);
		parseArgument(SpellArg.TARGET_PLAYER, jsonData, spellArgs, SpellValueType.TARGET_PLAYER);
		parseArgument(SpellArg.VALUE, jsonData, spellArgs, SpellValueType.INTEGER);
		parseArgument(SpellArg.SPELL_1, jsonData, spellArgs, SpellValueType.SPELL);
		parseArgument(SpellArg.SPELL_2, jsonData, spellArgs, SpellValueType.SPELL);
		parseArgument(SpellArg.SPELL_3, jsonData, spellArgs, SpellValueType.SPELL);
		parseArgument(SpellArg.ATTRIBUTE, jsonData, spellArgs, SpellValueType.ATTRIBUTE);
		return new SpellDesc(spellArgs);
	}

	private void parseArgument(SpellArg spellArg, JsonObject jsonData, Map<SpellArg, Object> spellArgs, SpellValueType valueType) {
		String argName = toCamelCase(spellArg.toString());
		System.out.println("ArgName: " + argName);
		if (!jsonData.has(argName)) {
			return;
		}
		Object value = null;
		JsonElement entry = jsonData.get(argName);
		switch (valueType) {
		case INTEGER:
			value = entry.getAsInt();
			break;
		case TARGET_REFERENCE:
			value = parseEntityReference(entry.getAsString());
			break;
		case TARGET_PLAYER:
			value = Enum.valueOf(TargetPlayer.class, entry.getAsString());
			break;
		case SPELL:
			value = deserialize(entry, SpellDesc.class, null);
			break;
		case ATTRIBUTE:
			value = Enum.valueOf(GameTag.class, entry.getAsString());
			break;
		default:
			break;
		}
		System.out.println("Setting spellArg " + spellArg + " to "  +value);
		spellArgs.put(spellArg, value);
	}
	
	private static EntityReference parseEntityReference(String str) {
		String lowerCaseName = str.toLowerCase();
		switch (lowerCaseName) {
		case "none":
			return EntityReference.NONE;
		case "enemy_characters":
			return EntityReference.ENEMY_CHARACTERS;
		case "enemy_minions":
			return EntityReference.ENEMY_MINIONS;
		case "enemy_hero":
			return EntityReference.ENEMY_HERO;
		case "enemy_weapon":
			return EntityReference.ENEMY_WEAPON;
		case "friendly_characters":
			return EntityReference.FRIENDLY_CHARACTERS;
		case "friendly_minions":
			return EntityReference.FRIENDLY_MINIONS;
		case "other_friendly_minions":
			return EntityReference.OTHER_FRIENDLY_MINIONS;
		case "adjacent_minions":
			return EntityReference.ADJACENT_MINIONS;
		case "friendly_hero":
			return EntityReference.FRIENDLY_HERO;
		case "friendly_weapon":
			return EntityReference.FRIENDLY_WEAPON;
		case "all_minions":
			return EntityReference.ALL_MINIONS;
		case "all_characters":
			return EntityReference.ALL_CHARACTERS;
		case "all_other_characters":
			return EntityReference.ALL_OTHER_CHARACTERS;
		case "event_target":
			return EntityReference.EVENT_TARGET;
		case "self":
			return EntityReference.SELF;
		default:
			return null;
		}
	}

	private static String toCamelCase(String input) {
		String inputLowerCase = input.toLowerCase();
		StringBuilder sb = new StringBuilder();
		final char delim = '_';
		char value;
		boolean capitalize = false;
		for (int i = 0; i < inputLowerCase.length(); ++i) {
			value = inputLowerCase.charAt(i);
			if (value == delim) {
				capitalize = true;
			} else if (capitalize) {
				sb.append(Character.toUpperCase(value));
				capitalize = false;
			} else {
				sb.append(value);
			}
		}

		return sb.toString();
	}

}
