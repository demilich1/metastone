package net.demilich.metastone.gui.battleofdecks;

import java.util.Collection;

import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.decks.Deck;

public class BattleConfig {

	private final int numberOfGames;
	private final IBehaviour behaviour;
	private final Collection<Deck> decks;

	public BattleConfig(int numberOfGames, IBehaviour behaviour, Collection<Deck> decks) {
		this.numberOfGames = numberOfGames;
		this.behaviour = behaviour;
		this.decks = decks;
	}

	public IBehaviour getBehaviour() {
		return behaviour;
	}

	public Collection<Deck> getDecks() {
		return decks;
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

}
