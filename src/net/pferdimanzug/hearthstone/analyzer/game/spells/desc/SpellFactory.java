package net.pferdimanzug.hearthstone.analyzer.game.spells.desc;

import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;

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
