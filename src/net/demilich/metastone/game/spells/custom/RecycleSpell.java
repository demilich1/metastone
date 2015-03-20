package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ShuffleMinionToDeckSpell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class RecycleSpell extends ShuffleMinionToDeckSpell {
	
	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(RecycleSpell.class);
		return new SpellDesc(arguments);
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Minion minion = (Minion) target;
		Player owner = context.getPlayer(minion.getOwner());
		context.getLogic().removeMinion(minion);
		super.onCast(context, owner, desc, source, target);
	}

}
