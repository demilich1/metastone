package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
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

		private void damage(GameContext context, Player player, Entity primaryTarget, Actor target) {
			int damage = target == primaryTarget ? primaryDamage : secondaryDamage;
			context.getLogic().damage(player, target, damage, getSource());
		}

		protected void onCast(GameContext context, Player player, Entity target) {
			damage(context, player, target, context.getOpponent(player).getHero());
			Player owner = context.getPlayer(target.getOwner());
			for (Actor minion : owner.getMinions()) {
				damage(context, player, target, minion);
			}
		}

	}

	public Swipe() {
		super("Swipe", Rarity.FREE, HeroClass.DRUID, 4);
		setDescription("Deal $4 damage to an enemy and $1 damage to all other enemies.");
		setSpell(new SwipeSpell(4, 1));
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
	}



	@Override
	public int getTypeId() {
		return 23;
	}
}
