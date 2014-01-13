package net.pferdimanzug.hearthstone.analyzer.game;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class Player implements Cloneable {

	private final String name;
	private final Hero hero;

	private final List<Minion> minions;
	private final CardCollection<Card> deck;
	private final CardCollection<Card> hand;
	private final CardCollection<Card> graveyard;

	private int mana;
	private int maxMana;
	
	private IBehaviour behaviour;

	public Player(String name, Hero hero, CardCollection<Card> deck) {
		this.name = name;
		this.hero = hero;
		this.deck = deck;
		this.minions = new ArrayList<>();
		this.hand = new CardCollection<>();
		this.graveyard = new CardCollection<>();
	}
	
	public IBehaviour getBehaviour() {
		return behaviour;
	}

	public List<Entity> getCharacters() {
		List<Entity> characters = new ArrayList<Entity>();
		characters.add(getHero());
		characters.addAll(getMinions());
		return characters;
	}

	public CardCollection<Card> getDeck() {
		return deck;
	}
	
	public CardCollection<Card> getGraveyard() {
		return graveyard;
	}

	public CardCollection<Card> getHand() {
		return hand;
	}

	public Hero getHero() {
		return hero;
	}

	public int getMana() {
		return mana;
	}
	
	public int getMaxMana() {
		return maxMana;
	}

	public List<Minion> getMinions() {
		return minions;
	}

	public String getName() {
		return "'" + name + "' (" + hero.getName() + ")";
	}

	public void setBehaviour(IBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}
	
}
