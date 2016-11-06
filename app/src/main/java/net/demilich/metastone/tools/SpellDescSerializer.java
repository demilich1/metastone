package net.demilich.metastone.tools;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.demilich.metastone.game.cards.desc.ParseUtils;
import net.demilich.metastone.game.cards.desc.SpellDeserializer;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SpellDescSerializer extends SpellDeserializer {
}
