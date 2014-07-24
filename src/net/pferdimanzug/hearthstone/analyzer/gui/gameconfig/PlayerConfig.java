package net.pferdimanzug.hearthstone.analyzer.gui.gameconfig;

import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;

public class PlayerConfig {

	private Hero hero;
	private Deck deck;
	private IBehaviour behaviour;

	public IBehaviour getBehaviour() {
		return behaviour;
	}

	public Deck getDeck() {
		return deck;
	}

	public Hero getHero() {
		return hero;
	}

	public void setBehaviour(IBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

}
