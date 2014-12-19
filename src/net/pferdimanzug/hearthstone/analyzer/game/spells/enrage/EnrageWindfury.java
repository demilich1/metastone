package net.pferdimanzug.hearthstone.analyzer.game.spells.enrage;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.WindfurySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.RemoveWindfurySpell;

public class EnrageWindfury extends Enrage {
	
	public static SpellDesc create(int attackBonus) {
		SpellDesc desc = new SpellDesc(EnrageWindfury.class);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		super.onCast(context, player, desc, target);
		if (target.hasStatus(GameTag.ENRAGED)) {
			SpellDesc windfurySpell = WindfurySpell.create();
			windfurySpell.setTarget(target.getReference());
			windfurySpell.setSourceEntity(desc.getSourceEntity());
			context.getLogic().castSpell(player.getId(), windfurySpell);
		} else {
			SpellDesc removeWindfurySpell = RemoveWindfurySpell.create();
			removeWindfurySpell.setTarget(target.getReference());
			removeWindfurySpell.setSourceEntity(desc.getSourceEntity());
			context.getLogic().castSpell(player.getId(), removeWindfurySpell);
		}
	}
	

}
