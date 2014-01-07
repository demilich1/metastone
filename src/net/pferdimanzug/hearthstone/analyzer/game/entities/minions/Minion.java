package net.pferdimanzug.hearthstone.analyzer.game.entities.minions;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;

public class Minion extends Entity {
	
	private Aura aura;

	public Minion(MinionCard sourceCard) {
		super(sourceCard);
	}
	
	@Override
	public EntityType getEntityType() {
		return EntityType.MINION;
	}

	public Aura getAura() {
		return aura;
	}

	public void setAura(Aura aura) {
		this.aura = aura;
	}
	
	protected void setBaseStats(int baseAttack, int baseHp) {
		setBaseAttack(baseAttack);
		setBaseHp(baseHp);
	}
	
	public boolean canAttackThisTurn() {
		return getAttack() > 0 && getTagValue(GameTag.NUMBER_OF_ATTACKS) > 0;
	}
}
