package net.demilich.metastone.tools;

import javafx.util.StringConverter;
import net.demilich.metastone.game.spells.Spell;

public class SpellStringConverter extends StringConverter<Class<? extends Spell>> {

	@Override
	public Class<? extends Spell> fromString(String arg0) {
		return null;
	}

	@Override
	public String toString(Class<? extends Spell> spell) {
		return spell.getSimpleName();
	}

}
