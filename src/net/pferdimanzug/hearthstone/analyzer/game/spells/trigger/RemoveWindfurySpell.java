package net.pferdimanzug.hearthstone.analyzer.game.spells.trigger;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;

public class RemoveWindfurySpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		if (!target.hasTag(GameTag.WINDFURY)) {
			return;
		}
		target.removeTag(GameTag.WINDFURY);
		target.modifyTag(GameTag.NUMBER_OF_ATTACKS, -1);
	}

}
