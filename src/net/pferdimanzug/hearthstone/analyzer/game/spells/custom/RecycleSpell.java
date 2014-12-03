package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ShuffleMinionToDeckSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
