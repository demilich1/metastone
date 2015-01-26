package net.demilich.metastone.game.spells.aura;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.targeting.EntityReference;

public class BuffAura extends Aura {

	private Race raceRestriction;

	public BuffAura(int attackBonus, int hpBonus, EntityReference targetSelection) {
		this(attackBonus, hpBonus, targetSelection, null);
	}

	public BuffAura(int attackBonus, int hpBonus, EntityReference targetSelection, Race raceRestriction) {
		super(AuraSpellBuff.create(attackBonus, hpBonus), AuraSpellBuff.create(-attackBonus, -hpBonus), targetSelection);
		this.raceRestriction = raceRestriction;
	}

	@Override
	protected boolean affects(GameContext context, Entity target, List<Entity> resolvedTargets) {
		if (!super.affects(context, target, resolvedTargets)) {
			return false;
		}

		if (raceRestriction != null && target.getEntityType() == EntityType.MINION) {
			Minion minion = (Minion) target;
			return minion.getRace() == raceRestriction;
		}
		return true;
	}

}
