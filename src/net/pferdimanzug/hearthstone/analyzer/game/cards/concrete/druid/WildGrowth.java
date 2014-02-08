package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class WildGrowth extends SpellCard {

	private class ExcessManaCard extends SpellCard {

		public ExcessManaCard() {
			super("Excess Mana", Rarity.FREE, HeroClass.DRUID, 0);
			setCollectible(false);
			setSpell(new DrawCardSpell(1));
			setTargetRequirement(TargetSelection.NONE);
		}
		
	}

	private class WildGrowthSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			if (player.getMaxMana() < GameLogic.MAX_MANA) {
				player.setMaxMana(player.getMaxMana() + 1);
			} else {
				player.getHand().add(new ExcessManaCard());
			}

		}
	}
	
	public WildGrowth() {
		super("Wild Growth", Rarity.FREE, HeroClass.DRUID, 2);
		setSpell(new WildGrowthSpell());
		setTargetRequirement(TargetSelection.NONE);
	}

}
