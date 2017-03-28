package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Summon;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class ShuffleMinionToDeckSpell extends ShuffleToDeckSpell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (!target.isDestroyed()) {
			context.getLogic().removeSummon((Summon) target, true);
		}

		super.onCast(context, player, desc, source, target);
	}

}
