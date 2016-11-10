package net.demilich.metastone.gui.deckbuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.demilich.metastone.game.decks.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.decks.validation.DefaultDeckValidator;
import net.demilich.metastone.game.decks.validation.IDeckValidator;
import net.demilich.nittygrittymvc.Proxy;

public class DeckProxy extends Proxy<GameNotification> {

	public static final String NAME = "DeckProxy";

	private final DeckCatalogue deckCatalogue = new DeckCatalogue();
	private IDeckValidator activeDeckValidator = new DefaultDeckValidator();
	private Deck activeDeck;

	public DeckProxy() {
		super(NAME);
		try {
			// ensure user's personal deck dir exists
			Files.createDirectories(Paths.get(DeckCatalogue.DECKS_FOLDER_PATH));
			// ensure decks have been copied to ~/metastone/decks
			DeckCatalogue.copyDecksFromResources();
		} catch (IOException e) {
			DeckCatalogue.logger.error("Trouble creating " + Paths.get(DeckCatalogue.DECKS_FOLDER_PATH));
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
		return DeckCatalogue.getDeckByName(deckName);
	}

	public List<Deck> getDecks() {
		return DeckCatalogue.getDecks();
	}

	public void deleteDeck(Deck deck) {
		DeckCatalogue.deleteDeck(deck);
		this.getFacade().sendNotification(GameNotification.DECKS_LOADED, DeckCatalogue.getDecks());
	}

	public void loadDecks() throws IOException, URISyntaxException {
		// load decks from ~/metastone/decks on the filesystem
		deckCatalogue.loadDecksFromFilesystem();
	}

	public boolean nameAvailable(Deck deck) {
		return DeckCatalogue.nameAvailable(deck);
	}

	public void removeCardFromDeck(Card card) {
		activeDeck.getCards().remove(card);
	}

	public void saveActiveDeck() {
		DeckCatalogue.getDecks().add(activeDeck);
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
			filename = DeckCatalogue.DECKS_FOLDER_PATH + File.separator + filename + ".json";
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
