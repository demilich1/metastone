package net.pferdimanzug.hearthstone.analyzer.game.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public abstract class Entity {

	private String name;
	private final HashMap<GameTag, Object> tags = new HashMap<GameTag, Object>();
	private Player owner;
	private final Card sourceCard;
	private final List<SpellTrigger> spellTriggers = new ArrayList<>();

	public Entity(Card sourceCard) {
		this.setName(sourceCard != null ? sourceCard.getName() : null); 
		this.sourceCard = sourceCard;
	}

	public abstract EntityType getEntityType();

	public void setTag(GameTag tag) {
		tags.put(tag, 1);
	}

	public void setTag(GameTag tag, int value) {
		tags.put(tag, value);
	}
	
	public void setTag(GameTag tag, Object value) {
		tags.put(tag, value);
	}

	public void removeTag(GameTag tag) {
		tags.remove(tag);
	}

	public boolean hasTag(GameTag tag) {
		return tags.get(tag) != null;
	}
	
	public Object getTag(GameTag tag) {
		return tags.get(tag);
	}

	public int getTagValue(GameTag tag) {
		return tags.containsKey(tag) ? (int)tags.get(tag) : 0;
	}

	public void modifyTag(GameTag tag, int value) {
		if (!hasTag(tag)) {
			setTag(tag, 0);
		}
		setTag(tag, getTagValue(tag) + value);
	}

	public int getHp() {
		return getTagValue(GameTag.HP);
	}

	public void setHp(int value) {
		setTag(GameTag.HP, value);
	}

	public void setBaseHp(int value) {
		setMaxHp(value);
		setHp(value);
	}

	public int getMaxHp() {
		return getTagValue(GameTag.MAX_HP) + getTagValue(GameTag.HP_BONUS);
	}

	public void setMaxHp(int value) {
		setTag(GameTag.MAX_HP, value);
	}
	
	public void modifyHpBonus(int value) {
		modifyTag(GameTag.HP_BONUS, value);
		modifyTag(GameTag.HP, value);
	}

	public boolean isDead() {
		return getHp() < 1;
	}

	public int getAttack() {
		return getTagValue(GameTag.BASE_ATTACK) + getTagValue(GameTag.ATTACK_BONUS);
	}

	public void setBaseAttack(int value) {
		setTag(GameTag.BASE_ATTACK, value);
	}

	public String getName() {
		return name;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public GameAction getBattlecry() {
		return (GameAction) getTag(GameTag.BATTLECRY);
	}
	
	public GameAction getDeathrattle() {
		return (GameAction) getTag(GameTag.DEATHRATTLE);
	}
	
	public ISpell getEnrageSpell() {
		return (ISpell) getTag(GameTag.ENRAGE_SPELL);
	}

	public void onAttack(Entity target) {
	}

	public Card getSourceCard() {
		return sourceCard;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isWounded() {
		return getHp() != getMaxHp();
	}
	
	public void addSpellTrigger(SpellTrigger spellTrigger) {
		spellTrigger.setHost(this);
		getSpellTriggers().add(spellTrigger);
	}

	public List<SpellTrigger> getSpellTriggers() {
		return spellTriggers;
	}
}
