package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;

public class SummonCopySpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(SummonCopySpell.class);
		return desc;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion template = (Minion) target;
		Minion clone = template.clone();
		clone.setSpellTrigger(null);
		
		context.getLogic().summon(player.getId(), clone);
		for (IGameEventListener trigger : context.getTriggersAssociatedWith(template.getReference())) {
			IGameEventListener triggerClone = trigger.clone();
			context.getLogic().addGameEventListener(player, triggerClone, clone);
		}
	}

}
