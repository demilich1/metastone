package net.demilich.metastone.game.cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.game.cards.desc.ChooseOneCardDesc;
import net.demilich.metastone.game.cards.desc.SpellCardDesc;
import net.demilich.metastone.game.cards.desc.SpellDescDeserializer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class CardParser {

	private static Logger logger = LoggerFactory.getLogger(CardParser.class);

	private final Gson gson;
	
	public CardParser() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(SpellDesc.class, new SpellDescDeserializer());
		gson = gsonBuilder.create();
	}

	public CardDesc parseCard(File file) {
		logger.info("Parsing " + file.getName() + "...");
		FileReader reader = null;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		JsonElement jsonData = gson.fromJson(reader, JsonElement.class);

		CardType type = CardType.valueOf((String) jsonData.getAsJsonObject().get("type").getAsString());
		System.out.println("CardType: " + type);
		switch (type) {
		case SPELL:
			return gson.fromJson(jsonData, SpellCardDesc.class);
		case CHOOSE_ONE:
			return gson.fromJson(jsonData, ChooseOneCardDesc.class);
		default:
			System.out.println("Unknown cardType: " + type);
			break;
		}
		return null;
	}
}
