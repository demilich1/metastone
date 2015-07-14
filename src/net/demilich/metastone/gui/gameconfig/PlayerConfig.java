package net.demilich.metastone.gui.gameconfig;

import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.cards.HeroCard;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.MetaDeck;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public class PlayerConfig {

	private String name;
	private HeroCard HeroCard;
	private Deck deck;
	private IBehaviour behaviour;
	private boolean hideCards;

	public PlayerConfig() {
	}

	public PlayerConfig(Deck deck, IBehaviour behaviour) {
		this.deck = deck;
		this.behaviour = behaviour;
	}

	public IBehaviour getBehaviour() {
		return behaviour;
	}

	public Deck getDeck() {
		return deck;
	}

	public Deck getDeckForPlay() {
		if (deck instanceof MetaDeck) {
			MetaDeck metaDeck = (MetaDeck) deck;
			return metaDeck.selectRandom();
		}
		return deck;
	}

	public HeroCard getHeroCard() {
		return HeroCard;
	}

	public HeroClass getHeroClass() {
		return deck.getHeroClass();
	}

	public String getName() {
		return name != null ? name : HeroCard.getName();
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

	public void setHeroCard(HeroCard HeroCard) {
		this.HeroCard = HeroCard;
	}

	public void setHideCards(boolean hideCards) {
		this.hideCards = hideCards;
	}

	public void setName(String name) {
		this.name = name;
	}

}
