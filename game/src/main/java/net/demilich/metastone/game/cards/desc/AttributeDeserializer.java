package net.demilich.metastone.game.cards.desc;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;

import com.google.gson.*;

import net.demilich.metastone.game.Attribute;

public class AttributeDeserializer implements JsonDeserializer<Map<Attribute, Object>>, JsonSerializer<Map<Attribute, Object>> {

	@Override
	public Map<Attribute, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Map<Attribute, Object> map = new EnumMap<Attribute, Object>(Attribute.class);
		JsonObject jsonData = json.getAsJsonObject();
		parseAttribute(Attribute.HP, jsonData, map, ParseValueType.INTEGER);//TODO Remove from Heroes
		parseAttribute(Attribute.MAX_HP, jsonData, map, ParseValueType.INTEGER);//TODO Remove from Heroes
		
		parseAttribute(Attribute.ATTACK_EQUALS_HP, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.BATTLECRY, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.BOTH_CHOOSE_ONE_OPTIONS, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.CANNOT_ATTACK, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.CANNOT_ATTACK_HERO_ON_SUMMON, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.CANNOT_ATTACK_HEROES, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.CHARGE, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.COMBO, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.DEATHRATTLES, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.DIVINE_SHIELD, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.DOUBLE_BATTLECRIES, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.DOUBLE_DEATHRATTLES, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.ENRAGABLE, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.HEAL_AMPLIFY_MULTIPLIER, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.HERO_POWER_CAN_TARGET_MINIONS, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.HERO_POWER_DAMAGE, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.HERO_POWER_USAGES, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.IMMUNE_HERO, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.INVERT_HEALING, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.MEGA_WINDFURY, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.OPPONENT_SPELL_DAMAGE, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.OVERLOAD, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.SPELL_AMPLIFY_MULTIPLIER, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.SPELL_DAMAGE, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.SPELL_DAMAGE_MULTIPLIER, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(Attribute.STEALTH, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.UNTARGETABLE_BY_SPELLS, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.AURA_UNTARGETABLE_BY_SPELLS, jsonData, map, ParseValueType.BOOLEAN);//TODO Remove from Spellstopper
		parseAttribute(Attribute.TAUNT, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(Attribute.WINDFURY, jsonData, map, ParseValueType.BOOLEAN);
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

	@Override
	public JsonElement serialize(Map<Attribute, Object> src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		for (Attribute attribute : Attribute.values()) {
			if (!src.containsKey(attribute)) {
				continue;
			}
			String argName = ParseUtils.toCamelCase(attribute.toString());
			result.add(argName, new JsonPrimitive(src.get(attribute).toString()));
		}
		return result;
	}
}
