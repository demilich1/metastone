package net.demilich.metastone.game.cards.desc;

import java.lang.reflect.Type;
import java.util.Map;

import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.spells.desc.filter.FilterArg;
import net.demilich.metastone.game.spells.desc.filter.FilterDesc;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class FilterDeserializer implements JsonDeserializer<FilterDesc> {
	@SuppressWarnings("unchecked")
	@Override
	public FilterDesc deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonObject)) {
			throw new JsonParseException("ValueProvider parser expected an JsonObject but found " + json + " instead");
		}
		JsonObject jsonData = (JsonObject) json;
		String filterClassName = EntityFilter.class.getPackage().getName() + "." + jsonData.get("class").getAsString();
		Class<? extends EntityFilter> filterClass;
		try {
			filterClass = (Class<? extends EntityFilter>) Class.forName(filterClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new JsonParseException("ValueProvider parser encountered an invalid class: " + filterClassName);
		}
		Map<FilterArg, Object> arguments = FilterDesc.build(filterClass);

		parseArgument(FilterArg.VALUE, jsonData, arguments, ParseValueType.INTEGER);
		parseArgument(FilterArg.ATTRIBUTE, jsonData, arguments, ParseValueType.ATTRIBUTE);
		parseArgument(FilterArg.RACE, jsonData, arguments, ParseValueType.RACE);
		parseArgument(FilterArg.OPERATION, jsonData, arguments, ParseValueType.OPERATION);

		return new FilterDesc(arguments);
	}

	private void parseArgument(FilterArg arg, JsonObject jsonData, Map<FilterArg, Object> arguments, ParseValueType valueType) {
		String argName = ParseUtils.toCamelCase(arg.toString());
		if (!jsonData.has(argName)) {
			return;
		}
		Object value = ParseUtils.parse(argName, jsonData, valueType);
		arguments.put(arg, value);
	}

}
