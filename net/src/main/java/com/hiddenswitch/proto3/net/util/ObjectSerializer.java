package com.hiddenswitch.proto3.net.util;

import com.amazonaws.util.StringInputStream;
import com.google.gson.*;
import com.hiddenswitch.proto3.net.models.Game;
import net.demilich.metastone.game.GameContext;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Base64;

public class ObjectSerializer<T extends Serializable> implements JsonSerializer<T>, JsonDeserializer<T> {

	@Override
	@SuppressWarnings("unchecked")
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (json.isJsonNull()) {
			return null;
		}
		JsonObject object = json.getAsJsonObject();
		byte bytes[] = Base64.getDecoder().decode(object.get("javaSerialized").getAsString());

		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream objectInputStream = null;
		T returnContext = null;
		try {
			objectInputStream = new ObjectInputStream(inputStream);
			returnContext = (T) objectInputStream.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e2) {
			return null;
		}
		return returnContext;
	}

	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
		if (src == null) {
			return JsonNull.INSTANCE;
		}
		String s = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ObjectOutputStream o = new ObjectOutputStream(output);
			o.writeObject(src);
			o.flush();
			s = Base64.getEncoder().encodeToString(output.toByteArray());
		} catch (IOException e) {
			return JsonNull.INSTANCE;
		}
		JsonObject object = new JsonObject();
		object.addProperty("javaSerialized", s);
		return object;
	}
}
