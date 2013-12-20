package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class PowerWordShield extends SpellCard {

	public PowerWordShield() {
		super("Power Word: Shield", Rarity.FREE, HeroClass.PRIEST, 1);
		setSpell(new PowerWordShieldSpell(2));
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	private class PowerWordShieldSpell extends BuffSpell {
		
		public PowerWordShieldSpell(int hpBonus) {
			super(0, hpBonus);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			context.getLogic().drawCard(player);
		}
		
		
		
	}

}
