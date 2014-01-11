package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

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

public class SavageRoar extends SpellCard {

	public SavageRoar() {
		super("Savage Roar", Rarity.FREE, HeroClass.DRUID, 3);
		setSpell(new SavageRoarSpell());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	private class SavageRoarSpell extends AreaSpell {
		
		private ISpell helperSpell;

		public SavageRoarSpell() {
			super(TargetSelection.FRIENDLY_CHARACTERS);
			SpellTrigger endBuffTrigger = new SpellTrigger(new TurnEndTrigger(), new BuffSpell(-2,0));
			helperSpell = new MetaSpell(new BuffSpell(+2, 0), new AddSpellTriggerSpell(endBuffTrigger));
		}

		@Override
		protected void forEachTarget(GameContext context, Player player, Entity entity) {
			helperSpell.cast(context, player, entity);
		}
		
	}

}
