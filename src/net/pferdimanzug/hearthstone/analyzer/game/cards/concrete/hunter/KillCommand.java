package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class KillCommand extends SpellCard {

	private class KillCommandSpell extends DamageSpell {

		private int beastDamage;

		public KillCommandSpell(int damage, int beastDamage) {
			super(damage);
			this.beastDamage = beastDamage;
		}

		private boolean hasBeast(Player player) {
			for (Actor minion : player.getMinions()) {
				if (minion.getRace() == Race.BEAST) {
					return true;
				}
			}
			return false;
		}
		
		@Override
		protected void onCast(GameContext context, Player player, Actor target) {
			int damage = hasBeast(player) ? beastDamage : getDamage();
			context.getLogic().damage(player, target, damage, true);
		}
		
	}
	
	public KillCommand() {
		super("Kill Command", Rarity.FREE, HeroClass.HUNTER, 3);
		setDescription("Deal $3 damage. If you have a Beast, deal $5 damage instead.");
		setSpell(new KillCommandSpell(3, 5));
		setTargetRequirement(TargetSelection.ANY);
	}

}
