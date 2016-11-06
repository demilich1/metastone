package net.demilich.metastone.game;

import java.io.Serializable;

import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.decks.ProceduralRandomDeck;
import net.demilich.metastone.game.decks.RandomDeck;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

public class ProceduralPlayer extends Player implements Serializable {
	private final CardCollection bench;
	
	public ProceduralPlayer(PlayerConfig config) {
		super(config);
		Deck selectedDeck = config.getDeckForPlay();
		this.bench = selectedDeck.getCardsCopy();

		this.deck = new ProceduralRandomDeck(config.getDeck().getHeroClass()).getCardsCopy();
		this.setHero(config.getHeroForPlay().createHero());
		this.setName(config.getName() + " - " + hero.getName());
		setBehaviour(config.getBehaviour().clone());
		setHideCards(config.hideCards());
	}
	
	public CardCollection getBench(){
		return bench;
	}

}
