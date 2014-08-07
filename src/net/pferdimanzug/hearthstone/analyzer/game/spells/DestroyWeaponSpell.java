package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class DestroyWeaponSpell extends ChangeDurabilitySpell {
	
	public static SpellDesc create() {
		return ChangeDurabilitySpell.create(-99);
	}

}
