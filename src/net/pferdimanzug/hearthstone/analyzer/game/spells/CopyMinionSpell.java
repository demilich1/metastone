package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class CopyMinionSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		Minion copy = ((Minion) target).clone();
		copy.removeTag(GameTag.BATTLECRY);
		copy.setOwner(player.getId());
		
		context.getLogic().summon(player.getId(), copy, null);
	}

}
