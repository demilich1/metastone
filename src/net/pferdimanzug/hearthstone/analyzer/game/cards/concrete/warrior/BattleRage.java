package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BattleRage extends SpellCard {

	private class BattleRageSpell extends DrawCardSpell {

		public BattleRageSpell() {
			super(0);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			int woundedCharacters = player.getHero().isWounded() ? 1 : 0;
			for (Actor minion : player.getMinions()) {
				if (minion.isWounded()) {
					woundedCharacters++;
				}
			}
			setNumberOfCards(woundedCharacters);
			super.onCast(context, player, target);
		}
		
	}
	
	public BattleRage() {
		super("Battle Rage", Rarity.COMMON, HeroClass.WARRIOR, 2);
		setDescription("Draw a card for each damaged friendly character.");
		setSpell(new BattleRageSpell());
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(EntityReference.NONE);
	}

}
