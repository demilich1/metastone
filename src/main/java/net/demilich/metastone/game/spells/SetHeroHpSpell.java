package net.demilich.metastone.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SetHeroHpSpell extends Spell {

	private static final Logger logger = LoggerFactory.getLogger(SetHeroHpSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Actor actor = (Actor) target;
		if (actor.getMaxHp() > desc.getValue()) {
			actor.setMaxHp(desc.getValue());
			logger.debug("{}'s Max Hp have been set to {}", actor, actor.getMaxHp());
		}
		actor.setHp(desc.getValue());
		logger.debug("{}'s Hp have been set to {}", actor, actor.getHp());
	}
}
