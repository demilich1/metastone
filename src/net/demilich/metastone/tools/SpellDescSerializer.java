package net.demilich.metastone.tools;

import java.lang.reflect.Type;

import net.demilich.metastone.game.cards.desc.ParseUtils;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SpellDescSerializer implements JsonSerializer<SpellDesc>{

	@Override
	public JsonElement serialize(SpellDesc spell, Type type, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("class", new JsonPrimitive(spell.getSpellClass().getSimpleName()));
		for (SpellArg spellArg : SpellArg.values()) {
			if (spellArg == SpellArg.CLASS) {
				continue;
			}
			if (!spell.contains(spellArg)) {
				continue;
			}
			String argName = ParseUtils.toCamelCase(spellArg.toString());
			result.add(argName, new JsonPrimitive(spell.get(spellArg).toString()));
		}
		return result;
	}

}
