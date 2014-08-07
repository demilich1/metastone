package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlexstraszaSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(AlexstraszaSpell.class);
		return desc;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(AlexstraszaSpell.class);
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Actor actor = (Actor) target;
		actor.setHp(15);
		logger.debug("{}'s Hp have been set to {}", actor, actor.getHp());
	}
}