package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.trigger.TriggerDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class AddSpellTriggerSpell extends Spell {

	public static SpellDesc create(EntityReference target, TriggerDesc trigger) {
		Map<SpellArg, Object> arguments = SpellDesc.build(AddSpellTriggerSpell.class);
		arguments.put(SpellArg.TRIGGER, trigger);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(TriggerDesc trigger) {
		return create(null, trigger);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		TriggerDesc triggerDesc = (TriggerDesc) desc.get(SpellArg.TRIGGER);
		SpellTrigger spellTrigger = triggerDesc.create();
		context.getLogic().addGameEventListener(player, spellTrigger, target);
	}

}
