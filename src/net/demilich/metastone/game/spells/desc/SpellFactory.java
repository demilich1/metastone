package net.demilich.metastone.game.spells.desc;

import net.demilich.metastone.game.spells.Spell;

public class SpellFactory {

	public Spell getSpell(SpellDesc spellDesc) {
		Class<? extends Spell> spellClass = spellDesc.getSpellClass();
		try {
			return spellClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
