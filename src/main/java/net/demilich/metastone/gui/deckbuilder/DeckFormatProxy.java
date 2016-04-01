package net.demilich.metastone.gui.deckbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.nittygrittymvc.Proxy;

public class DeckFormatProxy extends Proxy<GameNotification> {

	private static Logger logger = LoggerFactory.getLogger(DeckFormatProxy.class);

	public static final String NAME = "DeckFormatProxy";
	
	private static final String DECK_FORMATS_FOLDER = "./formats/";

	private final List<DeckFormat> deckFormats = new ArrayList<DeckFormat>();

	public DeckFormatProxy() {
		super(NAME);
	}

	public DeckFormat getDeckFormatByName(String deckName) {
		for (DeckFormat deckFormat : deckFormats) {
			if (deckFormat.getName().equals(deckName)) {
				return deckFormat;
			}
		}
		return null;
	}

	public List<DeckFormat> getDeckFormats() {
		return deckFormats;
	}

	public void loadDeckFormats() throws FileNotFoundException {
		deckFormats.clear();
		File folder = new File(DECK_FORMATS_FOLDER);
		if (!folder.exists()) {
			logger.warn("/decks/formats directory not found");
			return;
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Collection<File> files = FileUtils.listFiles(folder, new String[] { "json" }, true);
		loadDeckFormats(files, gson);
	}

	private void loadDeckFormats(Collection<File> files, Gson gson) throws FileNotFoundException {
		for (File file : files) {
			FileReader reader = new FileReader(file);
			HashMap<String, Object> map = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			if (!map.containsKey("sets")) {
				logger.error("Deck {} does not specify a value for 'sets' and is therefore not valid", file.getName());
				continue;
			}
			String deckName = (String) map.get("name");
			DeckFormat deckFormat = null;
			// this one is a meta deck; we need to parse those after all other
			// decks are done
			deckFormat = parseStandardDeckFormat(map);
			deckFormat.setName(deckName);
			deckFormat.setFilename(file.getName());
			deckFormats.add(deckFormat);
		}
	}

	private DeckFormat parseStandardDeckFormat(Map<String, Object> map) {
		DeckFormat deckFormat = new DeckFormat();
		@SuppressWarnings("unchecked")
		List<String> setIds = (List<String>) map.get("sets");
		for (String setId : setIds) {
			for (CardSet set : CardSet.values()) {
				if (set.toString().equalsIgnoreCase(setId)) {
					deckFormat.addSet(set);
				}
			}
		}
		return deckFormat;
		
	}

}
