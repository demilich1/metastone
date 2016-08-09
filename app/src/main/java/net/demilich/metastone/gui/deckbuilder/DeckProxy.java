package net.demilich.metastone.gui.deckbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.demilich.metastone.utils.MetastoneProperties;
import net.demilich.metastone.utils.ResourceInputStream;
import net.demilich.metastone.utils.ResourceLoader;
import net.demilich.metastone.utils.UserHomeMetastone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.decks.MetaDeck;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.decks.validation.DefaultDeckValidator;
import net.demilich.metastone.game.decks.validation.IDeckValidator;
import net.demilich.nittygrittymvc.Proxy;

public class DeckProxy extends Proxy<GameNotification> {

	private static Logger logger = LoggerFactory.getLogger(DeckProxy.class);

	public static final String NAME = "DeckProxy";
	private static final String DECKS_FOLDER = "decks";
	private static final String DECKS_FOLDER_PATH = UserHomeMetastone.getPath() + File.separator + DECKS_FOLDER;
	private static final String DECKS_COPIED_PROPERTY = "decks.copied";

	private final List<Deck> decks = new ArrayList<Deck>();
	private IDeckValidator activeDeckValidator = new DefaultDeckValidator();
	private Deck activeDeck;

	public DeckProxy() {
		super(NAME);
		try {
			// ensure user's personal deck dir exists
			Files.createDirectories(Paths.get(DECKS_FOLDER_PATH));
			// ensure decks have been copied to ~/metastone/decks
			copyDecksFromResources();
		} catch (IOException e) {
			logger.error("Trouble creating " + Paths.get(DECKS_FOLDER_PATH));
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public boolean addCardToDeck(Card card) {
		boolean result = activeDeckValidator.canAddCardToDeck(card, activeDeck);
		if (result) {
			activeDeck.getCards().add(card);
		}
		return result;
	}

	public Deck getActiveDeck() {
		return activeDeck;
	}

	public List<Card> getCards(HeroClass heroClass) {
		DeckFormat deckFormat = new DeckFormat();
		for (CardSet cardSet : CardSet.values()) {
			deckFormat.addSet(cardSet);
		}
		CardCollection cardCollection;
		if (activeDeck.isArbitrary()) {
			cardCollection = CardCatalogue.query(deckFormat);
		} else {
			cardCollection = CardCatalogue.query(deckFormat, heroClass);
			// add neutral cards
			cardCollection.addAll(CardCatalogue.query(deckFormat, HeroClass.ANY));
		}
		cardCollection.sortByName();
		cardCollection.sortByManaCost();
		return cardCollection.toList();
	}

	public Deck getDeckByName(String deckName) {
		for (Deck deck : decks) {
			if (deck.getName().equals(deckName)) {
				return deck;
			}
		}
		return null;
	}

	public List<Deck> getDecks() {
		return decks;
	}

	public void deleteDeck(Deck deck) {
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
		getFacade().sendNotification(GameNotification.DECKS_LOADED, decks);
	}

	public void loadDecks() throws IOException, URISyntaxException {
		decks.clear();

		// load decks from ~/metastone/decks on the filesystem
		loadStandardDecks(ResourceLoader.loadJsonInputStreams(DECKS_FOLDER_PATH, true), new GsonBuilder().setPrettyPrinting().create());

		loadMetaDecks(ResourceLoader.loadJsonInputStreams(DECKS_FOLDER_PATH, true), new GsonBuilder().setPrettyPrinting().create());
	}

	private void copyDecksFromResources() throws IOException, URISyntaxException {
		// if we have not copied decks to the USER_HOME_METASTONE decks folder,
		// then do so now
		if (!MetastoneProperties.getBoolean(DECKS_COPIED_PROPERTY)) {
			ResourceLoader.copyFromResources(DECKS_FOLDER, DECKS_FOLDER_PATH);

			// set a property to indicate that we have copied decks
			MetastoneProperties.setBoolean(DECKS_COPIED_PROPERTY, true);
		}
	}

	private void loadMetaDecks(Collection<ResourceInputStream> inputStreams, Gson gson) throws IOException {
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

	private void loadStandardDecks(Collection<ResourceInputStream> inputStreams, Gson gson) throws FileNotFoundException {
		for (ResourceInputStream resourceInputStream : inputStreams) {

			Reader reader = new InputStreamReader(resourceInputStream.inputStream);
			HashMap<String, Object> map = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			if (!map.containsKey("heroClass")) {
				logger.error("Deck {} does not speficy a value for 'heroClass' and is therefor not valid", resourceInputStream.fileName);
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

	public boolean nameAvailable(Deck deck) {
		for (Deck existingDeck : decks) {
			if (existingDeck != deck && existingDeck.getName().equals(deck.getName())) {
				return false;
			}
		}
		return true;
	}

	private Deck parseMetaDeck(Map<String, Object> map) {
		@SuppressWarnings("unchecked")
		List<String> referencedDecks = (List<String>) map.get("decks");
		List<Deck> decksInMetaDeck = new ArrayList<>();
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

	private Deck parseStandardDeck(String deckName, HeroClass heroClass, Map<String, Object> map) {
		boolean arbitrary = false;
		if (map.containsKey("arbitrary")) {
			arbitrary = (boolean) map.get("arbitrary");
		}
		Deck deck = new Deck(heroClass, arbitrary);
		@SuppressWarnings("unchecked")
		List<String> cardIds = (List<String>) map.get("cards");
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

	public void removeCardFromDeck(Card card) {
		activeDeck.getCards().remove(card);
	}

	public void saveActiveDeck() {
		decks.add(activeDeck);
		saveToJson(activeDeck);
		activeDeck = null;
	}

	private void saveToJson(Deck deck) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		HashMap<String, Object> saveData = new HashMap<String, Object>();
		saveData.put("name", deck.getName());
		saveData.put("description", deck.getDescription());
		saveData.put("arbitrary", deck.isArbitrary());
		saveData.put("heroClass", deck.getHeroClass());
		if (deck.isMetaDeck()) {
			MetaDeck metaDeck = (MetaDeck) deck;
			List<String> referencedDecks = new ArrayList<>();
			for (Deck referencedDeck : metaDeck.getDecks()) {
				referencedDecks.add(referencedDeck.getName());
			}
			saveData.put("decks", referencedDecks);
		} else {
			List<String> cardIds = new ArrayList<String>();
			for (Card card : deck.getCards()) {
				cardIds.add(card.getCardId());
			}
			saveData.put("cards", cardIds);
		}

		String jsonData = gson.toJson(saveData);
		try {
			String filename = deck.getName().toLowerCase();
			filename = filename.replaceAll(" ", "_");
			filename = filename.replaceAll("\\W+", "");
			filename = DECKS_FOLDER_PATH + File.separator + filename + ".json";
			Path path = Paths.get(filename);
			Files.write(path, jsonData.getBytes());
			deck.setFilename(path.getFileName().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setActiveDeck(Deck activeDeck) {
		this.activeDeck = activeDeck;
	}

	public void setActiveDeckValidator(IDeckValidator deckValidator) {
		this.activeDeckValidator = deckValidator;
	}

}
