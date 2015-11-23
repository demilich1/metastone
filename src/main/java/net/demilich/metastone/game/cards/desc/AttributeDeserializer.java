package net.demilich.metastone.game.cards.desc;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.demilich.metastone.game.Attribute;

public class AttributeDeserializer implements JsonDeserializer<Map<Attribute, Object>> {

	@Override
	public Map<Attribute, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Map<Attribute, Object> map = new EnumMap<Attribute, Object>(Attribute.class);
		JsonObject jsonData = json.getAsJsonObject();
		parseAttribute(Attribute.OVERLOAD, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.ATTACK_EQUALS_HP, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.CANNOT_ATTACK, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.CANNOT_ATTACK_HEROES, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.ENRAGABLE, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.CHARGE, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.DIVINE_SHIELD, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.STEALTH, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.UNTARGETABLE_BY_SPELLS, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.WINDFURY, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.MEGA_WINDFURY, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.TAUNT, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.OPPONENT_SPELL_DAMAGE, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.SPELL_DAMAGE, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.SPELL_AMPLIFY_MULTIPLIER, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.SPELL_DAMAGE_MULTIPLIER, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.INVERT_HEALING, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.HP, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.MAX_HP, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.HERO_POWER_USAGES, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.HERO_POWER_DAMAGE, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.BATTLECRY, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.DEATHRATTLES, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.DOUBLE_BATTLECRIES, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.DOUBLE_DEATHRATTLES, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.HERO_POWER_CAN_TARGET_MINIONS, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.MEATSHIELD, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.ARMOR_SUIT, jsonData, map, ParseValueType.BOOLEAN);
		return map;
	}

	private void parseAttribute(Attribute attribute, JsonObject jsonData, Map<Attribute, Object> map, ParseValueType valueType) {
		String argName = attribute.toString();
		if (!jsonData.has(argName)) {
			return;
		}
		Object value = ParseUtils.parse(argName, jsonData, valueType);
		Boolean bool = value instanceof Boolean ? (Boolean) value : null;
		if (bool != null && bool == true) {
			value = 1;
		}
		map.put(attribute, value);
	}

}
