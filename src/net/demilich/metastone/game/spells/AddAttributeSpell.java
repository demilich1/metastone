package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class AddAttributeSpell extends RevertableSpell {

	public static SpellDesc create(EntityReference target, GameTag tag) {
		return create(target, tag, null);
	}
	
	public static SpellDesc create(EntityReference target, GameTag tag, GameEventTrigger revertTrigger) {
		Map<SpellArg, Object> arguments = SpellDesc.build(AddAttributeSpell.class);
		arguments.put(SpellArg.ATTRIBUTE, tag);
		arguments.put(SpellArg.REVERT_TRIGGER, revertTrigger);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}
	
	public static SpellDesc create(GameTag tag) {
		return create(tag, null);
	}

	public static SpellDesc create(GameTag tag, GameEventTrigger revertTrigger) {
		return create(null, tag, revertTrigger);
	}

	@Override
	protected SpellDesc getReverseSpell(SpellDesc desc, EntityReference target) {
		return RemoveAttributeSpell.create(target, (GameTag) desc.get(SpellArg.ATTRIBUTE));
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		GameTag tag = (GameTag) desc.get(SpellArg.ATTRIBUTE);
		context.getLogic().applyTag(target, tag);
		super.onCast(context, player, desc, source, target);
	}
}
