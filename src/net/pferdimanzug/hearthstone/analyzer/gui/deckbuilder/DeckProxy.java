package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.validation.DefaultDeckValidator;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.validation.IDeckValidator;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import de.pferdimanzug.nittygrittymvc.Proxy;

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

	public List<Deck> getDecks() {
		return decks;
	}

	public Deck getDeckByName(String deckName) {
		for (Deck deck : decks) {
			if (deck.getName().equals(deckName)) {
				return deck;
			}
		}
		return null;
	}

	public void loadDecks() throws FileNotFoundException {
		decks.clear();
		File folder = new File("./decks/");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		for (File file : FileUtils.listFiles(folder, new String[] { "json" }, true)) {
			FileReader reader = new FileReader(file);
			HashMap<String, Object> map = gson.fromJson(reader, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			if (!map.containsKey("heroClass")) {
				logger.error("Deck {} does not speficy a value for 'heroClass' and is therefor not valid", file.getName());
				continue;
			}
			HeroClass heroClass = HeroClass.valueOf((String) map.get("heroClass"));
			String deckName = (String) map.get("name");
			Deck deck = new Deck(heroClass);
			deck.setName(deckName);
			@SuppressWarnings("unchecked")
			List<Double> cardIds = (List<Double>) map.get("cards");
			for (Double doubleCardId : cardIds) {
				int cardId = doubleCardId.intValue();
				Card card = CardCatalogue.getCardById(cardId);
				deck.getCards().add(card);
			}
			decks.add(deck);
		}
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
		List<Integer> cardIds = new ArrayList<Integer>();
		for (Card card : deck.getCards()) {
			cardIds.add(card.getTypeId());
		}
		saveData.put("cards", cardIds);
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
