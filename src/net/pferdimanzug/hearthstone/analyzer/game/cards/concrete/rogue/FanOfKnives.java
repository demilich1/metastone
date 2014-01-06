package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AreaDamageSpell;

public class FanOfKnives extends SpellCard {

	public FanOfKnives() {
		super("Fan of Knives", Rarity.FREE, HeroClass.ROGUE, 3);
		setSpell(new FanOfKnivesSpell());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	private class FanOfKnivesSpell extends AreaDamageSpell {

		public FanOfKnivesSpell() {
			super(1, TargetSelection.ENEMY_MINIONS);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			context.getLogic().drawCard(player);
		}
		
	}

}
