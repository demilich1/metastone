package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MindControlSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SilenceTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ShadowMadnessSpell extends MindControlSpell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ShadowMadnessSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		// mind control minion
		super.onCast(context, player, desc, target);
		
		// minion should be able to attack this turn
		target.removeTag(GameTag.SUMMONING_SICKNESS);
		context.getLogic().refreshAttacksPerRound(target);
		
		// mind control is terminated either when silenced or turn ends
		SpellDesc reverseMindcontrolSpell = ReverseMindControlSpell.create();
		reverseMindcontrolSpell.setSourceEntity(desc.getSourceEntity());
		reverseMindcontrolSpell.setTarget(EntityReference.SELF);
		SpellTrigger returnOnSilence = new SpellTrigger(new SilenceTrigger(), new TurnEndTrigger(), reverseMindcontrolSpell, true);
		context.getLogic().addGameEventListener(player, returnOnSilence, target);
	}
	
}