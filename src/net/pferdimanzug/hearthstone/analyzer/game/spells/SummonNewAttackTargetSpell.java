package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummonNewAttackTargetSpell extends Spell {
	
	public static SpellDesc create(MinionCard minionCard) {
		SpellDesc desc = new SpellDesc(SummonNewAttackTargetSpell.class);
		desc.set(SpellArg.CARD, minionCard);
		return desc;
	}
	
	public static Logger logger = LoggerFactory.getLogger(SummonNewAttackTargetSpell.class);
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		MinionCard minionCard = (MinionCard) desc.get(SpellArg.CARD);
		Minion targetMinion = ((MinionCard) minionCard.clone()).summon();
		context.getEnvironment().put(Environment.TARGET_OVERRIDE, targetMinion);
		context.getLogic().summon(player.getId(), targetMinion, null, null, false);
	}
	

}
