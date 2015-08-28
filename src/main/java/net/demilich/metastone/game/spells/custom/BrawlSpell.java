package net.demilich.metastone.game.spells.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BrawlSpell extends DestroySpell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(BrawlSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.ALL_MINIONS);
		return new SpellDesc(arguments);
	}

	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, Entity source, List<Entity> targets) {
		if (targets == null || targets.isEmpty()) {
			return;
		}
		List<Entity> destroyedTargets = new ArrayList<Entity>(targets);
		Entity randomTarget = SpellUtils.getRandomTarget(destroyedTargets);
		destroyedTargets.remove(randomTarget);
		for (Entity entity : destroyedTargets) {
			onCast(context, player, null, null, entity);
		}
	}
}
