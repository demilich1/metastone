package com.hiddenswitch.proto3.net.util;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFactory;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class AIPlayer extends Player {
	private transient Deck configuredDeck;

	public AIPlayer() {
		super();
		HeroClass[] heroClasses = {HeroClass.DRUID, HeroClass.HUNTER, HeroClass.MAGE, HeroClass.PALADIN, HeroClass.PRIEST, HeroClass.ROGUE, HeroClass.SHAMAN, HeroClass.WARLOCK, HeroClass.WARRIOR};
		Deck randomDeck = DeckFactory.getRandomDeck(
				heroClasses[RandomUtils.nextInt(0, heroClasses.length)],
				new DeckFormat().withCardSets(
						CardSet.PROCEDURAL_PREVIEW,
						CardSet.BASIC,
						CardSet.CLASSIC));
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
