package net.demilich.metastone.game.spells.aura;

import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.AuraBuffSpell;
import net.demilich.metastone.game.spells.desc.aura.AuraArg;
import net.demilich.metastone.game.spells.desc.aura.AuraDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BuffAura extends Aura {

	public BuffAura(AuraDesc desc) {
		this(desc.getInt(AuraArg.ATTACK_BONUS), desc.getInt(AuraArg.HP_BONUS), desc.getTarget(), desc.getRaceRestriction());
	}

	public BuffAura(int attackBonus, int hpBonus, EntityReference targetSelection, Race raceRestriction) {
		super(AuraBuffSpell.create(attackBonus, hpBonus), AuraBuffSpell.create(-attackBonus, -hpBonus), targetSelection);
		this.setRaceRestriction(raceRestriction);
	}
}
