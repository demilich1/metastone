package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class RemoveAttributeSpell extends Spell {
	public static SpellDesc create(EntityReference target, GameTag tag) {
		Map<SpellArg, Object> arguments = SpellDesc.build(RemoveAttributeSpell.class);
		arguments.put(SpellArg.ATTRIBUTE, tag);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(GameTag tag) {
		return create(null, tag);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		GameTag tag = (GameTag) desc.get(SpellArg.ATTRIBUTE);
		context.getLogic().removeTag(target, tag);
	}
}
