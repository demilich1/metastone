package net.pferdimanzug.hearthstone.analyzer.game.entities.minions;

import net.pferdimanzug.hearthstone.analyzer.game.aura.Aura;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;

public class Minion extends Entity {
	
	private Race race = Race.NONE;
	private Aura aura;

	public Minion(MinionCard sourceCard) {
		super(sourceCard);
	}
	
	@Override
	public EntityType getEntityType() {
		return EntityType.MINION;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
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
}
