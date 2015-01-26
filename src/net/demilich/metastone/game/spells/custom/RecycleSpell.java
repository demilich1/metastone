package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ShuffleMinionToDeckSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class RecycleSpell extends ShuffleMinionToDeckSpell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(RecycleSpell.class);
		return desc;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion minion = (Minion) target;
		Player owner = context.getPlayer(minion.getOwner());
		context.getLogic().removeMinion(minion);
		super.onCast(context, owner, desc, target);
	}

}
