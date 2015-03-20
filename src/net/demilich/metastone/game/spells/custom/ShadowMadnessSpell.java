package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.MindControlSpell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SilenceTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class ShadowMadnessSpell extends MindControlSpell {
	
	public static SpellDesc create() {
		return create(null);
	}
	public static SpellDesc create(EntityReference target) {
		Map<SpellArg, Object> arguments = SpellDesc.build(ShadowMadnessSpell.class);
		arguments.put(SpellArg.TARGET, target);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		// mind control minion
		super.onCast(context, player, desc, source, target);
		
		// minion should be able to attack this turn
		target.removeTag(GameTag.SUMMONING_SICKNESS);
		context.getLogic().refreshAttacksPerRound(target);
		
		// mind control is terminated either when silenced or turn ends
		SpellDesc reverseMindcontrolSpell = ReverseMindControlSpell.create(EntityReference.SELF);
		SpellTrigger returnOnSilence = new SpellTrigger(new SilenceTrigger(), new TurnEndTrigger(), reverseMindcontrolSpell, true);
		context.getLogic().addGameEventListener(player, returnOnSilence, target);
	}
	
}