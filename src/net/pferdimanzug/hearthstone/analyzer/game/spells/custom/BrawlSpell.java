package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class BrawlSpell extends DestroySpell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(BrawlSpell.class);
		return desc;
	}
	
	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		if (targets == null || targets.isEmpty()) {
			return;
		}
		List<Entity> destroyedTargets = new ArrayList<Entity>(targets);
		Entity randomTarget = SpellUtils.getRandomTarget(destroyedTargets);
		destroyedTargets.remove(randomTarget);
		for (Entity entity : destroyedTargets) {
			onCast(context, player, null, entity);
		}
	}
}
