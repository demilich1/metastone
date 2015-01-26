package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.spells.trigger.RemoveWindfurySpell;

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
