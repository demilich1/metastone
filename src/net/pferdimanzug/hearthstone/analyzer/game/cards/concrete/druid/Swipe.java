package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ISpell;

public class Swipe extends SpellCard {

	public Swipe() {
		super("Swipe", Rarity.FREE, HeroClass.DRUID, 4);
		setSpell(new SwipeSpell(4, 1));
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
	}

	private class SwipeSpell implements ISpell {

		private final int primaryDamage;
		private final int secondaryDamage;

		public SwipeSpell(int primaryDamage, int secondaryDamage) {
			this.primaryDamage = primaryDamage;
			this.secondaryDamage = secondaryDamage;
		}

		public void cast(GameContext context, Player player, Entity target) {
			damage(context, target, context.getOpponent(player).getHero());
			for (Minion minion : target.getOwner().getMinions()) {
				damage(context, target, minion);
			}
		}

		private void damage(GameContext context, Entity primaryTarget, Entity target) {
			int damage = target == primaryTarget ? primaryDamage : secondaryDamage;
			context.getLogic().damage(target, damage);
		}

	}

}
