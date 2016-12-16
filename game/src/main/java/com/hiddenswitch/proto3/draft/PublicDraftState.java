package com.hiddenswitch.proto3.draft;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.entities.heroes.HeroClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by bberman on 12/14/16.
 */
public class PublicDraftState {
	public List<HeroClass> heroClassChoices;
	public List<Card> currentCardChoices;
	public HeroClass heroClass;
	public DraftStatus status;
	public List<Card> selectedCards;
	public int cardsRemaining;
	public int draftIndex;
	public String deckId;

	public PublicDraftState() {
		this.currentCardChoices = Collections.emptyList();
		this.heroClassChoices = Collections.emptyList();
		this.status = DraftStatus.NOT_STARTED;
		this.selectedCards = new ArrayList<>();
		this.cardsRemaining = DraftLogic.DRAFTS;
		this.draftIndex = 0;
	}

	public Deck createDeck() {
		Deck deck = new Deck(this.heroClass);
		this.selectedCards.forEach(deck.getCards()::add);
		return deck;
	}
}
