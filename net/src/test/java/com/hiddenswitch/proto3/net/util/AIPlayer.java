package com.hiddenswitch.proto3.net.util;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFactory;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

import java.util.concurrent.atomic.AtomicInteger;

public class AIPlayer extends Player {
	private transient Deck configuredDeck;

	public AIPlayer() {
		super();
		Deck randomDeck = DeckFactory.getRandomDeck(HeroClass.MAGE, new DeckFormat().withCardSets(CardSet.PROCEDURAL_PREVIEW));
		setConfiguredDeck(randomDeck);
		buildFromConfig(new PlayerConfig(randomDeck, new AI()));
	}

	protected AIPlayer(PlayerConfig config) {
		super(config);
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
