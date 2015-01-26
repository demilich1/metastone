package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DestroyWeaponSpell extends ChangeDurabilitySpell {
	
	public static SpellDesc create() {
		return ChangeDurabilitySpell.create(-99);
	}

}
