package net.demilich.metastone.game.gameconfig;

import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.cards.HeroCard;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.MetaDeck;
import net.demilich.metastone.game.entities.heroes.MetaHero;

public class PlayerConfig {

	private String name;
	private HeroCard heroCard;
	private Deck deck;
	private IBehaviour behaviour;
	private boolean hideCards;

	private Deck deckForPlay;
	private HeroCard heroForPlay;

	public PlayerConfig() {
	}

	public PlayerConfig(Deck deck, IBehaviour behaviour) {
		this.deck = deck;
		this.behaviour = behaviour;
	}

	public void build() {
		if (deck instanceof MetaDeck) {
			MetaDeck metaDeck = (MetaDeck) deck;
			deckForPlay = metaDeck.selectRandom();

			heroForPlay = MetaHero.getHeroCard(deckForPlay.getHeroClass());
		} else {
			deckForPlay = deck;
			heroForPlay = heroCard;
		}
	}

	public IBehaviour getBehaviour() {
		return behaviour;
	}

	public Deck getDeck() {
		return deck;
	}

	public Deck getDeckForPlay() {
		return deckForPlay;
	}

	public HeroCard getHeroCard() {
		return heroCard;
	}

	public HeroCard getHeroForPlay() {
		return heroForPlay;
	}

	public String getName() {
		return name != null ? name : heroCard.getName();
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
		this.heroCard = HeroCard;
	}

	public void setHideCards(boolean hideCards) {
		this.hideCards = hideCards;
	}

	public void setName(String name) {
		this.name = name;
	}

}
