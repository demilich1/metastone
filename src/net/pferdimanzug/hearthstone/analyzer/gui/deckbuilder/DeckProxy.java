package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.validation.DefaultDeckValidator;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.validation.IDeckValidator;
import de.pferdimanzug.nittygrittymvc.Proxy;

public class DeckProxy extends Proxy<GameNotification> {

	public static final String NAME = "DeckProxy";

	private final List<Deck> decks = new ArrayList<Deck>();
	private final IDeckValidator deckValidator = new DefaultDeckValidator();
	private Deck activeDeck;

	public DeckProxy() {
		super(NAME);
	}

	public List<Deck> getDecks() {
		return decks;
	}

	public Deck getActiveDeck() {
		return activeDeck;
	}

	public void setActiveDeck(Deck activeDeck) {
		this.activeDeck = activeDeck;
	}

	public List<Card> getCards(HeroClass heroClass) {
		CardCollection cardCollection = CardCatalogue.query(null, null, heroClass);
		return cardCollection.toList();
	}

	public boolean addCardToDeck(Card card) {
		boolean result = deckValidator.canAddCardToDeck(card, activeDeck);
		if (result) {
			activeDeck.getCards().add(card);
		}
		return result;
	}

}
