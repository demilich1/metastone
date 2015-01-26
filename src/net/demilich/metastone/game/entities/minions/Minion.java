package net.demilich.metastone.game.entities.minions;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.EntityType;

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
		if (hasStatus(GameTag.ATTACK_EQUALS_HP)) {
			return getHp();
		}
		return super.getAttack();
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.MINION;
	}

	@Override
	public int getTypeId() {
		return getName().hashCode();
	}

	protected void setBaseStats(int baseAttack, int baseHp) {
		setBaseAttack(baseAttack);
		setBaseHp(baseHp);
	}

}
