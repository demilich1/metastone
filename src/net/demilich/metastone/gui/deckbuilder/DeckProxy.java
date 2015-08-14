package net.demilich.metastone.gui.deckbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import de.pferdimanzug.nittygrittymvc.Proxy;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.MetaDeck;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.gui.deckbuilder.validation.DefaultDeckValidator;
import net.demilich.metastone.gui.deckbuilder.validation.IDeckValidator;

public class DeckProxy extends Proxy<GameNotification> {

	private static Logger logger = LoggerFactory.getLogger(DeckProxy.class);

	public static final String NAME = "DeckProxy";

	private final List<Deck> decks = new ArrayList<Deck>();
	private final IDeckValidator deckValidator = new DefaultDeckValidator();
	private Deck activeDeck;

	public DeckProxy() {
		super(NAME);
	}

	public boolean addCardToDeck(Card card) {
		boolean result = deckValidator.canAddCardToDeck(card, activeDeck);
		if (result) {
			activeDeck.getCards().add(card);
		}
		return result;
	}

	public Deck getActiveDeck() {
		return activeDeck;
	}

	public List<Card> getCards(HeroClass heroClass) {
		CardCollection cardCollection = CardCatalogue.query(null, null, heroClass);
		// add neutral cards
		cardCollection.addAll(CardCatalogue.query(null, null, HeroClass.ANY));
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

	public void loadDecks() throws FileNotFoundException {
		decks.clear();
		File folder = new File("./decks/");
		if (!folder.exists()) {
			logger.warn("/decks directory not found");
			return;
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Collection<File> files = FileUtils.listFiles(folder, new String[] { "json" }, true);
		loadStandardDecks(files, gson);
		loadMetaDecks(files, gson);
	}

	private void loadMetaDecks(Collection<File> files, Gson gson) throws FileNotFoundException {
		for (File file : files) {
			FileReader reader = new FileReader(file);
			HashMap<String, Object> map = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			if (!map.containsKey("heroClass")) {
				logger.error("Deck {} does not specify a value for 'heroClass' and is therefor not valid", file.getName());
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
			decks.add(deck);
		}
	}

	private void loadStandardDecks(Collection<File> files, Gson gson) throws FileNotFoundException {
		for (File file : files) {
			FileReader reader = new FileReader(file);
			HashMap<String, Object> map = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			if (!map.containsKey("heroClass")) {
				logger.error("Deck {} does not speficy a value for 'heroClass' and is therefor not valid", file.getName());
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
				deck = parseStandardDeck(heroClass, map);
			}
			deck.setName(deckName);
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

	private Deck parseStandardDeck(HeroClass heroClass, Map<String, Object> map) {
		Deck deck = new Deck(heroClass);
		@SuppressWarnings("unchecked")
		List<String> cardIds = (List<String>) map.get("cards");
		for (String cardId : cardIds) {
			Card card = CardCatalogue.getCardById(cardId);
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
			filename = "./decks/" + filename + ".json";
			Files.write(Paths.get(filename), jsonData.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setActiveDeck(Deck activeDeck) {
		this.activeDeck = activeDeck;
	}

}
