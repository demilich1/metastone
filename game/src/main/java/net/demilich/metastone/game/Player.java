package net.demilich.metastone.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.behaviour.human.HumanBehaviour;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.statistics.GameStatistics;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

public class Player extends Entity {

	private Hero hero;
	private final String deckName;

	private final CardCollection deck;
	private final CardCollection hand = new CardCollection();
	private final List<Entity> setAsideZone = new ArrayList<>();
	private final List<Entity> graveyard = new ArrayList<>();
	private final List<Minion> minions = new ArrayList<>();
	private final HashSet<String> secrets = new HashSet<>();

	private final GameStatistics statistics = new GameStatistics();

	private int mana;
	private int maxMana;
	private int lockedMana;

	private boolean hideCards;

	private IBehaviour behaviour;

	private Player(Player otherPlayer) {
		this.setName(otherPlayer.getName());
		this.deckName = otherPlayer.getDeckName();
		this.setHero(otherPlayer.getHero().clone());
		this.deck = otherPlayer.getDeck().clone();
		this.attributes.putAll(otherPlayer.getAttributes());
		this.hand.addAll(otherPlayer.getHand().clone());
		this.minions.addAll(otherPlayer.getMinions().stream().map(Minion::clone).collect(Collectors.toList()));
		this.graveyard.addAll(otherPlayer.getGraveyard().stream().map(Entity::clone).collect(Collectors.toList()));
		this.setAsideZone.addAll(otherPlayer.getSetAsideZone().stream().map(Entity::clone).collect(Collectors.toList()));
		this.secrets.addAll(otherPlayer.secrets);
		this.setId(otherPlayer.getId());
		this.mana = otherPlayer.mana;
		this.maxMana = otherPlayer.maxMana;
		this.lockedMana = otherPlayer.lockedMana;
		this.behaviour = otherPlayer.behaviour;
		this.getStatistics().merge(otherPlayer.getStatistics());
	}

	public Player(PlayerConfig config) {
		config.build();
		Deck selectedDeck = config.getDeckForPlay();
		this.deck = selectedDeck.getCardsCopy();
		this.setHero(config.getHeroForPlay().createHero());
		this.setName(config.getName() + " - " + hero.getName());
		this.deckName = selectedDeck.getName();
		setBehaviour(config.getBehaviour().clone());
		setHideCards(config.hideCards());
	}

	@Override
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

	public String getDeckName() {
		return deckName;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.PLAYER;
	}

	public List<Entity> getGraveyard() {
		return graveyard;
	}

	public CardCollection getHand() {
		return hand;
	}

	public Hero getHero() {
		return hero;
	}

	public int getLockedMana() {
		return lockedMana;
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

	public HashSet<String> getSecrets() {
		return secrets;
	}
	
	public List<Entity> getSetAsideZone() {
		return setAsideZone;
	}

	public GameStatistics getStatistics() {
		return statistics;
	}

	public boolean hideCards() {
		return hideCards && !(behaviour instanceof HumanBehaviour);
	}

	public void setBehaviour(IBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public void setHideCards(boolean hideCards) {
		this.hideCards = hideCards;
	}

	public void setLockedMana(int lockedMana) {
		this.lockedMana = lockedMana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	@Override
	public String toString() {
		return "[PLAYER " + "id: " + getId() + ", name: " + getName() + ", hero: " + getHero() + "]";
	}

}
