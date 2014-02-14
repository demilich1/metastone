package net.pferdimanzug.hearthstone.analyzer.game.entities.minions;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;

public class Minion extends Actor {
	
	private Aura aura;

	public Minion(MinionCard sourceCard) {
		super(sourceCard);
		Race race = sourceCard.hasTag(GameTag.RACE) ? (Race)sourceCard.getTag(GameTag.RACE) : Race.NONE;
		setRace(race);
	}
	
	@Override
	public Minion clone() {
		return (Minion) super.clone();
	}

	public Aura getAura() {
		return aura;
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.MINION;
	}
	
	public void setAura(Aura aura) {
		this.aura = aura;
	}
	
	protected void setBaseStats(int baseAttack, int baseHp) {
		setBaseAttack(baseAttack);
		setBaseHp(baseHp);
	}

	@Override
	public int getAttack() {
		if (hasTag(GameTag.ATTACK_EQUALS_HP)) {
			return getHp();
		}
		return super.getAttack();
	}

	public Race getRace() {
		return (Race) getTag(GameTag.RACE);
	}

	public void setRace(Race race) {
		setTag(GameTag.RACE, race);
	}
	
	
	
}
