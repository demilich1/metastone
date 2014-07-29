package net.pferdimanzug.hearthstone.analyzer.game;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;

public class Player implements Cloneable {

	private final String name;
	private Hero hero;

	private final CardCollection deck;
	private final CardCollection hand = new CardCollection();
	private final List<Minion> graveyard = new ArrayList<>();
	private final List<Minion> minions = new ArrayList<>();
	private final List<Integer> secrets = new ArrayList<>();
	
	private final GameStatistics statistics = new GameStatistics();
	
	private int id = -1;

	private int mana;
	private int maxMana;
	
	private IBehaviour behaviour;

	private Player(Player otherPlayer) {
		this.name = otherPlayer.name;
		this.setHero(otherPlayer.getHero().clone());
		this.deck = otherPlayer.getDeck().clone();
		for (Minion minion : otherPlayer.getMinions()) {
			minions.add(minion.clone());
		}
		for (Card card : otherPlayer.hand) {
			this.hand.add(card.clone());
		}
		this.graveyard.addAll(otherPlayer.graveyard);
		this.secrets.addAll(otherPlayer.secrets);
		this.id = otherPlayer.id;
		this.mana = otherPlayer.mana;
		this.maxMana = otherPlayer.maxMana;
		this.behaviour = otherPlayer.behaviour;
		this.getStatistics().merge(otherPlayer.getStatistics());
	}
	
	public Player(String name, Hero hero, Deck deck) {
		this.name = name;
		this.setHero(hero);
		this.deck = deck.getCards().clone();
	}
	
	public Player clone() {
		return new Player(this);
	}

	public IBehaviour getBehaviour() {
		return behaviour;
	}

	public List<Actor> getCharacters() {
		List<Actor> characters = new ArrayList<Actor>();
		characters.add(getHero());
		characters.addAll(getMinions());
		return characters;
	}
	
	public CardCollection getDeck() {
		return deck;
	}

	public List<Minion> getGraveyard() {
		return graveyard;
	}

	public CardCollection getHand() {
		return hand;
	}

	public Hero getHero() {
		return hero;
	}
	
	public int getId() {
		return id;
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
		return "'" + name + "' (" + getHero().getName() + ")";
	}

	public List<Integer> getSecrets() {
		return secrets;
	}

	public void setBehaviour(IBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public GameStatistics getStatistics() {
		return statistics;
	}

}
