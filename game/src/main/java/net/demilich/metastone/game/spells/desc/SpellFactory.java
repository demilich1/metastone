package net.demilich.metastone.game.spells.desc;

import net.demilich.metastone.game.spells.Spell;

import java.io.Serializable;

public class SpellFactory implements Serializable {

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
