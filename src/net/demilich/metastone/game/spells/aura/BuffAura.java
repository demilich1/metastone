package net.demilich.metastone.game.spells.aura;

import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.targeting.EntityReference;

public class BuffAura extends Aura {

	public BuffAura(int attackBonus, int hpBonus, EntityReference targetSelection) {
		this(attackBonus, hpBonus, targetSelection, null);
	}

	public BuffAura(int attackBonus, int hpBonus, EntityReference targetSelection, Race raceRestriction) {
		super(AuraSpellBuff.create(attackBonus, hpBonus), AuraSpellBuff.create(-attackBonus, -hpBonus), targetSelection);
		this.setRaceRestriction(raceRestriction);
	}
}
