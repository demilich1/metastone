package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.utils.MathUtils;

public class HealToFullSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(HealToFullSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Actor actor = (Actor) target;
		int missingHp = MathUtils.clamp(actor.getMaxHp() - actor.getHp(), 0, 9999);
		context.getLogic().heal(player, actor, missingHp, desc.getSource());
	}

}
