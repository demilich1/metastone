package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class RemoveTagSpell extends Spell {
	public static SpellDesc create(GameTag tag) {
		return create(null, tag);
	}

	public static SpellDesc create(EntityReference target, GameTag tag) {
		Map<SpellArg, Object> arguments = SpellDesc.build(RemoveTagSpell.class);
		arguments.put(SpellArg.GAME_TAG, tag);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		GameTag tag = (GameTag) desc.get(SpellArg.GAME_TAG);
		context.getLogic().removeTag(target, tag);
	}
}
