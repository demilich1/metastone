package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ComboSpell implements ISpell {

	private ISpell noCombo;
	private ISpell combo;

	public ComboSpell(ISpell noCombo, ISpell combo) {
		this.noCombo = noCombo;
		this.combo = combo;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		if (player.getHero().hasTag(GameTag.COMBO)) {
			context.getLogic().castSpell(player, combo, target);
		} else {
			context.getLogic().castSpell(player, noCombo, target);
		}
		
	}

}
