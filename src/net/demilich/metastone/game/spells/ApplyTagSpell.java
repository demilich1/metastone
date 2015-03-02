package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;

public class ApplyTagSpell extends RevertableSpell {

	public static SpellDesc create(GameTag tag) {
		return create(tag, null);
	}

	public static SpellDesc create(GameTag tag, GameEventTrigger revertTrigger) {
		SpellDesc desc = new SpellDesc(ApplyTagSpell.class);
		desc.set(SpellArg.GAME_TAG, tag);
		desc.set(SpellArg.REVERT_TRIGGER, revertTrigger);
		return desc;
	}

	@Override
	protected SpellDesc getReverseSpell(SpellDesc desc) {
		return RemoveTagSpell.create((GameTag) desc.get(SpellArg.GAME_TAG));
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		GameTag tag = (GameTag) desc.get(SpellArg.GAME_TAG);
		context.getLogic().applyTag(target, tag);
		super.onCast(context, player, desc, target);
	}
}
