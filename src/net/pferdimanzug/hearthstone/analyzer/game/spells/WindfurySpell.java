package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.RemoveWindfurySpell;

public class WindfurySpell extends ApplyTagSpell {
	
	public static SpellDesc create() {
		return create((GameEventTrigger)null);
	}
	
	public static SpellDesc create(GameEventTrigger revertTrigger) {
		SpellDesc desc = ApplyTagSpell.create(GameTag.WINDFURY, revertTrigger);
		desc.setSpellClass(WindfurySpell.class);
		return desc;
	}

	@Override
	protected SpellDesc getReverseSpell(SpellDesc desc) {
		return RemoveWindfurySpell.create();
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		if (target.hasStatus(GameTag.WINDFURY)) {
			return;
		}
		target.modifyTag(GameTag.NUMBER_OF_ATTACKS, +1);
		super.onCast(context, player, desc, target);
	}
	

}
