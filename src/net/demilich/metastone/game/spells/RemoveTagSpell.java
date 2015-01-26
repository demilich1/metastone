package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveTagSpell extends Spell {

	public static SpellDesc create(GameTag tag) {
		SpellDesc desc = new SpellDesc(RemoveTagSpell.class);
		desc.set(SpellArg.GAME_TAG, tag);
		return desc;
	}

	private static Logger logger = LoggerFactory.getLogger(RemoveTagSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		GameTag tag = (GameTag) desc.get(SpellArg.GAME_TAG);
		logger.debug("Removing tag {} from {}", tag, target);
		target.removeTag(tag);
	}
}
