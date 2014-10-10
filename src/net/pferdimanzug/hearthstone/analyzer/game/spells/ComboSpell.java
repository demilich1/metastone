package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class ComboSpell extends EitherOrSpell {
	
	public static SpellDesc create(SpellDesc noCombo, SpellDesc combo) {
		return EitherOrSpell.create(combo, noCombo, (context, player, target) -> player.getHero().hasStatus(GameTag.COMBO));
	}

}
