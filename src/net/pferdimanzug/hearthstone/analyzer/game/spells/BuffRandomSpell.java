package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class BuffRandomSpell extends BuffSpell {
	
	public static SpellDesc create(int attackBonus, int hpBonus) {
		SpellDesc desc = new SpellDesc(BuffRandomSpell.class);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		if (hpBonus > 0) {
			desc.set(SpellArg.HP_BONUS, hpBonus);
		}
		return desc;
	}
	
	@Override
	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		if (targets == null || targets.isEmpty()) {
			return;
		}
		Entity randomTarget = SpellUtils.getRandomTarget(targets);
		onCast(context, player, desc, randomTarget);
	}

}
