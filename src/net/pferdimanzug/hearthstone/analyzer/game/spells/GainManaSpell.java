package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GainManaSpell extends Spell {

	public static SpellDesc create(int mana) {
		SpellDesc desc = new SpellDesc(GainManaSpell.class);
		desc.set(SpellArg.MANA, mana);
		return desc;
	}

	private static Logger logger = LoggerFactory.getLogger(GainManaSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int mana = desc.getInt(SpellArg.MANA);
		logger.debug("{} gains {} mana", player.getName(), mana);
		context.getLogic().modifyCurrentMana(player.getId(), mana);
	}

}
