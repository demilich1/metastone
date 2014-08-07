package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
