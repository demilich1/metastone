package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

public class Bloodlust extends SpellCard {

private class BloodlustSpell extends AreaSpell {
	
	private static final int ATTACK_BONUS = 3;
		
		private ISpell helperSpell;

		public BloodlustSpell() {
			super(TargetSelection.FRIENDLY_MINIONS);
			SpellTrigger endBuffTrigger = new SpellTrigger(new TurnEndTrigger(), new BuffSpell(-ATTACK_BONUS,0));
			helperSpell = new MetaSpell(new BuffSpell(+ATTACK_BONUS, 0), new AddSpellTriggerSpell(endBuffTrigger));
		}

		@Override
		protected void forEachTarget(GameContext context, Player player, Entity entity) {
			helperSpell.cast(context, player, entity);
		}
	}
	
	public Bloodlust() {
		super("Bloodlust", Rarity.FREE, HeroClass.SHAMAN, 5);
		setSpell(new BloodlustSpell());
		setTargetRequirement(TargetSelection.NONE);
	}

}
