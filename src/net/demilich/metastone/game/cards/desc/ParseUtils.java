package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.condition.ConditionDesc;
import net.demilich.metastone.game.spells.desc.filter.FilterDesc;
import net.demilich.metastone.game.spells.desc.filter.Operation;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProviderDesc;
import net.demilich.metastone.game.targeting.CardLocation;
import net.demilich.metastone.game.targeting.EntityReference;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ParseUtils {

	private static SpellDescDeserializer spellDescParser = new SpellDescDeserializer();
	private static ValueProviderDeserializer valueProviderParser = new ValueProviderDeserializer();
	private static FilterDeserializer filterParser = new FilterDeserializer();
	private static ConditionDeserializer conditionParser = new ConditionDeserializer();

	public static Object parse(String argName, JsonObject jsonData, ParseValueType valueType) {
		JsonElement entry = jsonData.get(argName);
		switch (valueType) {
		case INTEGER:
			return entry.getAsInt();
		case BOOLEAN:
			return entry.getAsBoolean();
		case STRING:
			return entry.getAsString();
		case STRING_ARRAY:
			JsonArray jsonArray = entry.getAsJsonArray();
			String[] array = new String[jsonArray.size()];
			for (int i = 0; i < array.length; i++) {
				array[i] = jsonArray.get(i).getAsString();
			}
			return array;
		case TARGET_REFERENCE:
			return parseEntityReference(entry.getAsString());
		case TARGET_PLAYER:
			return Enum.valueOf(TargetPlayer.class, entry.getAsString());
		case RACE:
			return Enum.valueOf(Race.class, entry.getAsString());
		case SPELL:
			return spellDescParser.deserialize(entry, SpellDesc.class, null);
		case ATTRIBUTE:
			return Enum.valueOf(GameTag.class, entry.getAsString());
		case BOARD_POSITION_RELATIVE:
			return Enum.valueOf(RelativeToSource.class, entry.getAsString());
		case CARD_LOCATION:
			return Enum.valueOf(CardLocation.class, entry.getAsString());
		case OPERATION:
			return Enum.valueOf(Operation.class, entry.getAsString());
		case VALUE_PROVIDER:
			ValueProviderDesc valueProviderDesc = valueProviderParser.deserialize(entry, ValueProviderDesc.class, null);
			return valueProviderDesc.create();
		case ENTITY_FILTER:
			FilterDesc filterDesc = filterParser.deserialize(entry, FilterDesc.class, null);
			return filterDesc.create();
		case CONDITION:
			ConditionDesc conditionDesc = conditionParser.deserialize(entry, ConditionDesc.class, null);
			return conditionDesc.create();
		default:
			break;
		}
		return null;
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
		case "target":
			return EntityReference.TARGET;
		case "self":
			return EntityReference.SELF;
		default:
			return null;
		}
	}

	public static String toCamelCase(String input) {
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

	public static boolean tryParseBool(String value) {
		try {
			Boolean.parseBoolean(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
