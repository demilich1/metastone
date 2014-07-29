package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MindControlSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SilenceTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ShadowMadness extends SpellCard {

	public ShadowMadness() {
		super("Shadow Madness", Rarity.RARE, HeroClass.PRIEST, 4);
		setDescription("Gain control of an enemy minion with 3 or less Attack until end of turn.");
		
		setSpell(new ShadowMadnessSpell());
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}
	
	@Override
	public boolean canBeCastOn(Entity target) {
		if (!super.canBeCastOn(target)) {
			return false;
		}
		Minion minion = (Minion) target;
		return minion.getAttack() <= 3;
	}


	@Override
	public int getTypeId() {
		return 278;
	}
	
	private class ReverseMindControlSpell extends MindControlSpell {
		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Player opponent = context.getOpponent(player);
			super.onCast(context, opponent, target);
		}
	}



	private class ShadowMadnessSpell extends MindControlSpell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			// mind control minion
			super.onCast(context, player, target);
			
			// minion should be able to attack this turn
			target.removeTag(GameTag.SUMMONING_SICKNESS);
			context.getLogic().refreshAttacksPerRound(target);
			
			// mind control is terminated either when silenced or turn ends
			Spell reverseMindcontrolSpell = new ReverseMindControlSpell();
			reverseMindcontrolSpell.setTarget(EntityReference.SELF);
			SpellTrigger returnOnSilence = new SpellTrigger(new SilenceTrigger(), new TurnEndTrigger(), reverseMindcontrolSpell, true);
			context.getLogic().addGameEventListener(player, returnOnSilence, target);
		}
		
	}
}
