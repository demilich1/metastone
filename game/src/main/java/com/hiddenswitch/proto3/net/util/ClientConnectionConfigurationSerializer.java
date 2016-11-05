package com.hiddenswitch.proto3.net.util;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;

public class ClientConnectionConfigurationSerializer extends ObjectSerializer<ClientConnectionConfiguration> implements JsonSerializer<ClientConnectionConfiguration>, JsonDeserializer<ClientConnectionConfiguration> {
}
