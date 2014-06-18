package net.pferdimanzug.hearthstone.analyzer.game.entities.minions;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
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

	@Override
	public Minion clone() {
		return (Minion) super.clone();
	}

	@Override
	public int getAttack() {
		if (hasTag(GameTag.ATTACK_EQUALS_HP)) {
			return getHp();
		}
		return super.getAttack();
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.MINION;
	}

	public Race getRace() {
		return (Race) getTag(GameTag.RACE);
	}

	protected void setBaseStats(int baseAttack, int baseHp) {
		setBaseAttack(baseAttack);
		setBaseHp(baseHp);
	}

	public void setBattlecry(Battlecry battlecry) {
		setTag(GameTag.BATTLECRY, battlecry);
	}

	public void setDeathrattle(Spell deathrattleSpell) {
		setTag(GameTag.DEATHRATTLE, deathrattleSpell);
	}

	public void setRace(Race race) {
		setTag(GameTag.RACE, race);
	}

}
