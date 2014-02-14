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
	
	
	
}
