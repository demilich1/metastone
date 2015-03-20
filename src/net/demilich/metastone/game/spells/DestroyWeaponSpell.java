package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DestroyWeaponSpell extends ChangeDurabilitySpell {
	
	public static SpellDesc create(EntityReference target) {
		return ChangeDurabilitySpell.create(target, -99);
	}

}
