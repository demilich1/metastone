package net.demilich.metastone.game.cards.desc;

import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.GameTag;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class AttributeDeserializer implements JsonDeserializer<Map<GameTag, Object>> {

	@Override
	public Map<GameTag, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Map<GameTag, Object> map = new EnumMap<GameTag, Object>(GameTag.class);
		JsonObject jsonData = json.getAsJsonObject();
		System.out.println("Parsing ATTRIBUTES");
		parseAttribute(GameTag.OVERLOAD, jsonData, map, ParseValueType.INTEGER);
		parseAttribute(GameTag.ATTACK_EQUALS_HP, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(GameTag.CANNOT_ATTACK, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(GameTag.CHARGE, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(GameTag.DIVINE_SHIELD, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(GameTag.STEALTH, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(GameTag.UNTARGETABLE_BY_SPELLS, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(GameTag.WINDFURY, jsonData, map, ParseValueType.BOOLEAN);
		parseAttribute(GameTag.SPELL_DAMAGE, jsonData, map, ParseValueType.INTEGER);
		return map;
	}

	private void parseAttribute(GameTag attribute, JsonObject jsonData, Map<GameTag, Object> map, ParseValueType valueType) {
		String argName = attribute.toString();
		if (!jsonData.has(argName)) {
			return;
		}
		Object value = ParseUtils.parse(argName, jsonData, valueType);
		map.put(attribute, value);
	}

}
