package com.hiddenswitch.proto3.net.util;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import net.demilich.metastone.game.GameContext;

public class GameContextSerializer extends ObjectSerializer<GameContext> implements JsonSerializer<GameContext>, JsonDeserializer<GameContext> {
}
