package net.pferdimanzug.hearthstone.analyzer.game.entities.minions;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;

public class Minion extends Actor {

	public Minion(MinionCard sourceCard) {
		super(sourceCard);
		Race race = sourceCard.hasTag(GameTag.RACE) ? (Race) sourceCard.getTag(GameTag.RACE) : Race.NONE;
		setRace(race);
	}

	@Override
	public Minion clone() {
		Minion clone = (Minion) super.clone();
		return clone;
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

	@Override
	public int getTypeId() {
		return getName().hashCode();
	}

	protected void setBaseStats(int baseAttack, int baseHp) {
		setBaseAttack(baseAttack);
		setBaseHp(baseHp);
	}

	public void setRace(Race race) {
		setTag(GameTag.RACE, race);
	}

}
