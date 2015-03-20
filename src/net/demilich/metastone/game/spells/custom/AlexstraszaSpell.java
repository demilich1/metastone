package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlexstraszaSpell extends Spell {
	
	public static SpellDesc create() {
		return new SpellDesc(SpellDesc.build(AlexstraszaSpell.class));
	}
	
	private static final Logger logger = LoggerFactory.getLogger(AlexstraszaSpell.class);
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Actor actor = (Actor) target;
		actor.setHp(15);
		logger.debug("{}'s Hp have been set to {}", actor, actor.getHp());
	}
}