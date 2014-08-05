package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.Environment;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummonNewAttackTargetSpell extends Spell {
	
	public static Logger logger = LoggerFactory.getLogger(SummonNewAttackTargetSpell.class);
	
	public SummonNewAttackTargetSpell(MinionCard minionCard) {
		setCloneableData(minionCard);
	}
	
	public MinionCard getMinionCard() {
		return (MinionCard) getCloneableData()[0];
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Minion targetMinion = getMinionCard().summon();
		context.getEnvironment().put(Environment.TARGET_OVERRIDE, targetMinion);
		context.getLogic().summon(player.getId(), targetMinion, null, null, false);
	}
	

}
