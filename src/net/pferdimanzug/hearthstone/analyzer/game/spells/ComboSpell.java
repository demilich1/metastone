package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;

public class ComboSpell extends EitherOrSpell {

	public ComboSpell(Spell noCombo, Spell combo) {
		super(combo, noCombo, (context, player, target) -> player.getHero().hasTag(GameTag.COMBO));
	}

}
