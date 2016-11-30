package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;
import net.demilich.metastone.game.targeting.EntityReference;

public class SummonCopySpell extends Spell {

	public static SpellDesc create(EntityReference target) {
		Map<SpellArg, Object> arguments = SpellDesc.build(SummonCopySpell.class);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Minion template = (Minion) target;
		int value = desc.getValue(SpellArg.VALUE, context, player, target, source, 1);
			for (int i = 0; i < value; i++) {
			Minion clone = template.clone();
			clone.clearSpellTriggers();
	
			context.getLogic().summon(player.getId(), clone);
			for (IGameEventListener trigger : context.getTriggersAssociatedWith(template.getReference())) {
				IGameEventListener triggerClone = trigger.clone();
				context.getLogic().addGameEventListener(player, triggerClone, clone);
			}
		}
	}

}
