package net.demilich.metastone.game.spells;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class GainManaSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(GainManaSpell.class);

	public static SpellDesc create(int mana) {
		Map<SpellArg, Object> arguments = SpellDesc.build(GainManaSpell.class);
		arguments.put(SpellArg.VALUE, mana);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int mana = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);
		logger.debug("{} gains {} mana", player.getName(), mana);
		context.getLogic().modifyCurrentMana(player.getId(), mana);
	}

}
