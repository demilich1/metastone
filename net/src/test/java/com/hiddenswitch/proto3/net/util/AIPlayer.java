package com.hiddenswitch.proto3.net.util;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFactory;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import org.apache.commons.lang3.RandomUtils;

public class AIPlayer extends Player {
	private transient Deck configuredDeck;

	public AIPlayer() {
		this(DeckFactory.getRandomDeck());
	}

	public AIPlayer(Deck deck) {
		super();
		setConfiguredDeck(deck);
		buildFromConfig(new PlayerConfig(deck, new AI()));
	}

	public Deck getConfiguredDeck() {
		return configuredDeck;
	}

	public void setConfiguredDeck(Deck configuredDeck) {
		this.configuredDeck = configuredDeck;
	}

	public AIPlayer withConfiguredDeck(Deck configuredDeck) {
		setConfiguredDeck(configuredDeck);
		return this;
	}
}
