package net.demilich.metastone.game.decks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.logic.ProceduralGameLogic;
import net.demilich.metastone.utils.MetastoneProperties;
import net.demilich.metastone.utils.ResourceInputStream;
import net.demilich.metastone.utils.ResourceLoader;
import net.demilich.metastone.utils.UserHomeMetastone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DeckCatalogue {
	private static final String DECKS_FOLDER = "decks";
	public static final String DECKS_FOLDER_PATH = UserHomeMetastone.getPath() + File.separator + DECKS_FOLDER;
	private static final String DECKS_COPIED_PROPERTY = "decks.copied";
	public static Logger logger = LoggerFactory.getLogger(DeckCatalogue.class);
	private static final List<Deck> decks = new ArrayList<Deck>();

	public static Deck getDeckByName(String deckName) {
		for (Deck deck : decks) {
			if (deck.getName().equals(deckName)) {
				return deck;
			}
		}
		return null;
	}

	public static List<Deck> getDecks() {
		return decks;
	}

	public static void deleteDeck(Deck deck) {
		decks.remove(deck);
		logger.debug("Trying to delete deck '{}' contained in file '{}'...", deck.getName(), deck.getFilename());
		Path path = Paths.get(DECKS_FOLDER_PATH + File.separator + deck.getFilename());
		try {
			Files.delete(path);
		} catch (NoSuchFileException x) {
			logger.error("Could not delete deck '{}' as the filename '{}' does not exist", deck.getName(), path);
			return;
		} catch (IOException e) {
			logger.error(e.getMessage());
			logger.error("Could not delete file '{}'", path);
			return;
		}

		logger.info("Deck '{}' contained in file '{}' has been successfully deleted", deck.getName(), path.getFileName().toString());
	}

	public static void loadDecks() throws IOException, URISyntaxException {
		decks.clear();

		// load decks from ~/metastone/decks on the filesystem
		loadStandardDecks(ResourceLoader.loadJsonInputStreams(DECKS_FOLDER_PATH, true), new GsonBuilder().setPrettyPrinting().create());

		loadMetaDecks(ResourceLoader.loadJsonInputStreams(DECKS_FOLDER_PATH, true), new GsonBuilder().setPrettyPrinting().create());
	}

	public static void copyDecksFromResources() throws IOException, URISyntaxException {
		// if we have not copied decks to the USER_HOME_METASTONE decks folder,
		// then do so now
		if (!MetastoneProperties.getBoolean(DECKS_COPIED_PROPERTY)) {
			ResourceLoader.copyFromResources(DECKS_FOLDER, DECKS_FOLDER_PATH);

			// set a property to indicate that we have copied decks
			MetastoneProperties.setBoolean(DECKS_COPIED_PROPERTY, true);
		}
	}

	private static void loadMetaDecks(Collection<ResourceInputStream> inputStreams, Gson gson) throws IOException {
		for (ResourceInputStream resourceInputStream : inputStreams) {
			Reader reader = new InputStreamReader(resourceInputStream.inputStream);
			HashMap<String, Object> map = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			if (!map.containsKey("heroClass")) {
				logger.error("Deck {} does not specify a value for 'heroClass' and is therefor not valid", resourceInputStream.fileName);
				continue;
			}
			String deckName = (String) map.get("name");
			Deck deck = null;
			if (!map.containsKey("decks")) {
				continue;
			} else {
				deck = parseMetaDeck(map);
			}
			deck.setName(deckName);
			deck.setFilename(resourceInputStream.fileName);
			decks.add(deck);
		}
	}

	private static void loadStandardDecks(Collection<ResourceInputStream> inputStreams, Gson gson) throws FileNotFoundException {
		for (ResourceInputStream resourceInputStream : inputStreams) {

			Reader reader = new InputStreamReader(resourceInputStream.inputStream);
			HashMap<String, Object> map = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			if (!map.containsKey("heroClass")) {
				logger.error("Deck {} does not specify a value for 'heroClass' and is therefore not valid", resourceInputStream.fileName);
				continue;
			}
			HeroClass heroClass = HeroClass.valueOf((String) map.get("heroClass"));
			String deckName = (String) map.get("name");
			Deck deck = null;
			// this one is a meta deck; we need to parse those after all other
			// decks are done
			if (map.containsKey("decks")) {
				continue;
			} else {
				deck = parseStandardDeck(deckName, heroClass, map);
			}
			deck.setName(deckName);
			deck.setFilename(resourceInputStream.fileName);
			decks.add(deck);
		}
	}

	public static boolean nameAvailable(Deck deck) {
		for (Deck existingDeck : decks) {
			if (existingDeck != deck && existingDeck.getName().equals(deck.getName())) {
				return false;
			}
		}
		return true;
	}

	private static Deck parseMetaDeck(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<String> referencedDecks = (List<String>) map.get("decks");
		List<Deck> decksInMetaDeck = new ArrayList<Deck>();
		for (String deckName : referencedDecks) {
			Deck deck = getDeckByName(deckName);
			if (deck == null) {
				logger.error("Metadeck {} contains invalid reference to deck {}", map.get("name"), deckName);
				continue;
			}
			decksInMetaDeck.add(deck);
		}
		return new MetaDeck(decksInMetaDeck);
	}

	private static Deck parseStandardDeck(String deckName, HeroClass heroClass, Map<String, Object> map) {
		boolean arbitrary = false;
		if (map.containsKey("arbitrary")) {
			arbitrary = (boolean) map.get("arbitrary");
		}
		Deck deck = new Deck(heroClass, arbitrary);
		@SuppressWarnings("unchecked")
		List<String> cardIds = (List<String>) map.get("cards");

		//TODO: fix bench exporting to json.
		if (cardIds.size() == ProceduralGameLogic.BENCH_SIZE) {
			deck = new Bench(heroClass, arbitrary);
		}

		for (String cardId : cardIds) {
			Card card = CardCatalogue.getCardById(cardId);
			if (card == null) {
				logger.error("Deck {} contains invalid cardId '{}'", deckName, cardId);
				continue;
			}
			deck.getCards().add(card);
		}
		return deck;
	}
}