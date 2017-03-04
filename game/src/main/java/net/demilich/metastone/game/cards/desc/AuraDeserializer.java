package net.demilich.metastone.game.cards.desc;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.*;

import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.desc.aura.AuraArg;
import net.demilich.metastone.game.spells.desc.aura.AuraDesc;

public class AuraDeserializer implements JsonDeserializer<AuraDesc>, JsonSerializer<AuraDesc> {

	@SuppressWarnings("unchecked")
	@Override
	public AuraDesc deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonObject)) {
			throw new JsonParseException("Aura parser expected an JsonObject but found " + json + " instead");
		}
		JsonObject jsonData = (JsonObject) json;
		String auraClassName = Aura.class.getPackage().getName() + "." + jsonData.get("class").getAsString();
		Class<? extends Aura> auraClass;
		try {
			auraClass = (Class<? extends Aura>) Class.forName(auraClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new JsonParseException("Aura parser encountered an invalid class: " + auraClassName);
		}
		Map<AuraArg, Object> arguments = AuraDesc.build(auraClass);
		parseArgument(AuraArg.FILTER, jsonData, arguments, ParseValueType.ENTITY_FILTER);
		parseArgument(AuraArg.TARGET, jsonData, arguments, ParseValueType.TARGET_REFERENCE);
		parseArgument(AuraArg.ATTRIBUTE, jsonData, arguments, ParseValueType.ATTRIBUTE);
		parseArgument(AuraArg.APPLY_EFFECT, jsonData, arguments, ParseValueType.SPELL);
		parseArgument(AuraArg.REMOVE_EFFECT, jsonData, arguments, ParseValueType.SPELL);
		parseArgument(AuraArg.ATTACK_BONUS, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(AuraArg.HP_BONUS, jsonData, arguments, ParseValueType.INTEGER);

		return new AuraDesc(arguments);
	}

	private void parseArgument(AuraArg arg, JsonObject jsonData, Map<AuraArg, Object> arguments, ParseValueType valueType) {
		String argName = ParseUtils.toCamelCase(arg.toString());
		if (!jsonData.has(argName)) {
			return;
		}
		Object value = ParseUtils.parse(argName, jsonData, valueType);
		arguments.put(arg, value);
	}

	@Override
	public JsonElement serialize(AuraDesc src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("class", new JsonPrimitive(src.getAuraClass().getSimpleName()));
		for (AuraArg spellArg : AuraArg.values()) {
			if (spellArg == AuraArg.CLASS) {
				continue;
			}
			if (!src.contains(spellArg)) {
				continue;
			}
			String argName = ParseUtils.toCamelCase(spellArg.toString());
			result.add(argName, new JsonPrimitive(src.get(spellArg).toString()));
		}
		return result;
	}
}
