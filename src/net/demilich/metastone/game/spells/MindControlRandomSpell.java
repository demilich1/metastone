package net.demilich.metastone.game.spells;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class MindControlRandomSpell extends MindControlSpell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(MindControlRandomSpell.class);
		return desc;
	}
	
	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		if (targets == null || targets.isEmpty()) {
			return;
		}
		int randomIndex = context.getLogic().random(targets.size());
		onCast(context, player, null, targets.get(randomIndex));
	}

}
