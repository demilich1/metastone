package net.demilich.metastone.game.spells;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SetHeroHpSpell extends Spell {

	private static final Logger logger = LoggerFactory.getLogger(SetHeroHpSpell.class);

	public static SpellDesc create(int value) {
		Map<SpellArg, Object> arguments = SpellDesc.build(SetHeroHpSpell.class);
		arguments.put(SpellArg.VALUE, value);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Actor actor = (Actor) target;
		int value = desc.getValue(SpellArg.VALUE, context, player, target, source, 0);
		if (actor.getMaxHp() < value) {
			actor.setMaxHp(value);
			logger.debug("{}'s Max Hp have been set to {}", actor, actor.getMaxHp());
		}
		actor.setHp(value);
		logger.debug("{}'s Hp have been set to {}", actor, actor.getHp());
	}
}
