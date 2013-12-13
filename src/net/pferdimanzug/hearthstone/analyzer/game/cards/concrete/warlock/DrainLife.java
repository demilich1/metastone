package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SingleTargetDamageSpell;

public class DrainLife extends SpellCard {

	public DrainLife() {
		super("Drain Life", Rarity.FREE, HeroClass.WARLOCK, 3);
		//TODO: can this be cast on own hero?
		setTargetRequirement(TargetRequirement.ANY);
		setSpell(new DrainLifeSpell(2));
	}
	
	private class DrainLifeSpell extends SingleTargetDamageSpell {

		public DrainLifeSpell(int damage) {
			super(damage);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			context.getLogic().heal(player.getHero(), getDamage());
		}
		
	}

}
