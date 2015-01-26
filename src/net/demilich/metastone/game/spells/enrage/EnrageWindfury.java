package net.demilich.metastone.game.spells.enrage;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.WindfurySpell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.RemoveWindfurySpell;

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
