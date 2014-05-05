package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TargetAcquisitionTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummonNewAttackTargetSpell extends Spell {
	
	public static Logger logger = LoggerFactory.getLogger(SummonNewAttackTargetSpell.class);
	
	private MinionCard minionCard;

	public SummonNewAttackTargetSpell(MinionCard minionCard) {
		this.minionCard = minionCard;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		Minion targetMinion = minionCard.summon();
		SpellTrigger spellTrigger = new SpellTrigger(new TargetAcquisitionTrigger(), new NullSpell(), true);
		if (targetMinion.hasSpellTrigger()) {
			logger.warn("New target of attack already has a SpellTrigger, will be OVERRIDDEN");
		}
		targetMinion.setSpellTrigger(spellTrigger);
		context.getLogic().summon(player.getId(), targetMinion, null, null);
		
	}
	

}
