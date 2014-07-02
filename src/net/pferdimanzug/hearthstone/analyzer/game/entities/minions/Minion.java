package net.pferdimanzug.hearthstone.analyzer.game.entities.minions;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;

public class Minion extends Actor {

	public Minion(MinionCard sourceCard) {
		super(sourceCard);
		Race race = sourceCard.hasTag(GameTag.RACE) ? (Race) sourceCard.getTag(GameTag.RACE) : Race.NONE;
		setRace(race);
	}

	public void addDeathrattle(Spell deathrattleSpell) {
		if (!hasTag(GameTag.DEATHRATTLES)) {
			setTag(GameTag.DEATHRATTLES, new ArrayList<Spell>());
		}
		getDeathrattles().add(deathrattleSpell);
	}

	@Override
	public Minion clone() {
		Minion clone =(Minion) super.clone(); 
		if (hasTag(GameTag.DEATHRATTLES)) {
			clone.removeTag(GameTag.DEATHRATTLES);
			for (Spell deathrattleSpell : getDeathrattles()) {
				clone.addDeathrattle(deathrattleSpell);
			}
		}
		return clone;
	}

	@Override
	public int getAttack() {
		if (hasTag(GameTag.ATTACK_EQUALS_HP)) {
			return getHp();
		}
		return super.getAttack();
	}

	@SuppressWarnings("unchecked")
	public List<Spell> getDeathrattles() {
		return (List<Spell>) getTag(GameTag.DEATHRATTLES);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.MINION;
	}

	public Race getRace() {
		return (Race) getTag(GameTag.RACE);
	}

	@Override
	public int getTypeId() {
		return getName().hashCode();
	}
	
	public Card returnToHand() {
		return getSourceCard();
	}

	protected void setBaseStats(int baseAttack, int baseHp) {
		setBaseAttack(baseAttack);
		setBaseHp(baseHp);
	}
	
	public void setBattlecry(Battlecry battlecry) {
		setTag(GameTag.BATTLECRY, battlecry);
	}
	
	public void setRace(Race race) {
		setTag(GameTag.RACE, race);
	}

}
