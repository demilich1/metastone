package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GainManaSpell extends Spell {

	public static SpellDesc create(int mana) {
		SpellDesc desc = new SpellDesc(GainManaSpell.class);
		desc.set(SpellArg.MANA, mana);
		desc.setTarget(EntityReference.NONE);
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
