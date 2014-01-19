package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Swipe extends SpellCard {

	private class SwipeSpell extends Spell {

		private final int primaryDamage;
		private final int secondaryDamage;

		public SwipeSpell(int primaryDamage, int secondaryDamage) {
			this.primaryDamage = primaryDamage;
			this.secondaryDamage = secondaryDamage;
		}

		private void damage(GameContext context, Entity primaryTarget, Entity target) {
			int damage = target == primaryTarget ? primaryDamage : secondaryDamage;
			context.getLogic().damage(target, damage);
		}

		protected void onCast(GameContext context, Player player, Entity target) {
			damage(context, target, context.getOpponent(player).getHero());
			Player owner = context.getPlayer(target.getOwner());
			for (Entity minion : owner.getMinions()) {
				damage(context, target, minion);
			}
		}

	}

	public Swipe() {
		super("Swipe", Rarity.FREE, HeroClass.DRUID, 4);
		setSpell(new SwipeSpell(4, 1));
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
	}

}
