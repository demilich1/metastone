package net.pferdimanzug.hearthstone.analyzer.gui.gameconfig;

import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroTemplate;

public class PlayerConfig {

	private HeroTemplate heroTemplate;
	private Deck deck;
	private IBehaviour behaviour;
	private boolean hideCards;

	public IBehaviour getBehaviour() {
		return behaviour;
	}

	public Deck getDeck() {
		return deck;
	}

	public boolean hideCards() {
		return hideCards;
	}

	public void setBehaviour(IBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public void setHideCards(boolean hideCards) {
		this.hideCards = hideCards;
	}

	public HeroTemplate getHeroTemplate() {
		return heroTemplate;
	}

	public void setHeroTemplate(HeroTemplate heroTemplate) {
		this.heroTemplate = heroTemplate;
	}

}
