package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;

public class BattleRage extends SpellCard {

	public BattleRage() {
		super("Battle Rage", Rarity.COMMON, HeroClass.WARRIOR, 2);
		setSpell(new BattleRageSpell());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	private class BattleRageSpell extends DrawCardSpell {

		public BattleRageSpell() {
			super(0);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			int woundedCharacters = player.getHero().isWounded() ? 1 : 0;
			for (Entity minion : player.getMinions()) {
				if (minion.isWounded()) {
					woundedCharacters++;
				}
			}
			setNumberOfCards(woundedCharacters);
			super.cast(context, player, target);
		}
		
		
		
	}

}
