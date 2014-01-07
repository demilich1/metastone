package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;

public class SacrificialPact extends SpellCard {

	public SacrificialPact() {
		super("Sacrificial Pact", Rarity.FREE, HeroClass.WARLOCK, 0);
		setSpell(new SacrificialPactSpell());
		setTargetRequirement(TargetSelection.ANY);
	}
	
	@Override
	public boolean canBeCastOn(Entity target) {
		return target.getRace() == Race.DEMON;
	}

	private class SacrificialPactSpell extends DestroySpell {

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			context.getLogic().heal(player.getHero(), 5);
		}
		
	}
	
	

}
