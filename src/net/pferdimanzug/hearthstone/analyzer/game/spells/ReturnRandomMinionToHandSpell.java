package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class ReturnRandomMinionToHandSpell extends ReturnMinionToHandSpell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ReturnRandomMinionToHandSpell.class);
		return desc;
	}

	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		if (targets == null || targets.isEmpty()) {
			return;
		}
		
		onCast(context, player, desc, SpellUtils.getRandomTarget(targets));
	}

}
