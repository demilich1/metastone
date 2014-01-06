package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;

public class ShieldBlock extends SpellCard {

	public ShieldBlock() {
		super("Shield Block", Rarity.FREE, HeroClass.WARRIOR, 3);
		setSpell(new ShieldBlockSpell());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	private class ShieldBlockSpell extends BuffHeroSpell {

		public ShieldBlockSpell() {
			super(0, 5);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			context.getLogic().drawCard(player);
		}

	}

}
