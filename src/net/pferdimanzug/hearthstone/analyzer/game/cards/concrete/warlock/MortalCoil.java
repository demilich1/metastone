package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class MortalCoil extends SpellCard {

	public MortalCoil() {
		super("Mortal Coil", Rarity.FREE, HeroClass.WARLOCK, 1);
		setSpell(new MortalCoilSpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	private class MortalCoilSpell extends SingleTargetDamageSpell {

		public MortalCoilSpell() {
			super(1);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			if (target.isDead()) {
				context.getLogic().drawCard(player);
			}
		}
		
	}

}
