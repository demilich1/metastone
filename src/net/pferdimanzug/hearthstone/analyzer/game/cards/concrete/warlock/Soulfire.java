package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DiscardCardSpell;

public class Soulfire extends SpellCard {

	public Soulfire() {
		super("Soulfire", Rarity.FREE, HeroClass.WARLOCK, 0);
		setSpell(new SoulfireSpell());
		setTargetRequirement(TargetSelection.ANY);
	}
	
	private class SoulfireSpell extends DiscardCardSpell {

		public SoulfireSpell() {
			super(1);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			context.getLogic().damage(target, 4);
			super.cast(context, player, target);
		}
		
		
		
	}
	

}
