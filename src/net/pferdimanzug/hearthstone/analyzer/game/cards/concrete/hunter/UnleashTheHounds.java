package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class UnleashTheHounds extends SpellCard {

	private class Hound extends MinionCard {

		public Hound() {
			super("Hound", 1, 1, Rarity.FREE, HeroClass.HUNTER, 1);
			setDescription("Charge");
			setCollectible(false);
			setTag(GameTag.RACE, Race.BEAST);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.CHARGE);
		}
		
	}
	
	private class UnleashTheHoundsSpell extends SummonSpell {
		public UnleashTheHoundsSpell() {
			super(new Hound());
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			int enemyMinions = context.getMinionCount(context.getOpponent(player));
			for (int i = 0; i < enemyMinions; i++) {
				super.onCast(context, player, target);
			}
		}
	}
	
	public UnleashTheHounds() {
		super("Unleash the Hounds", Rarity.COMMON, HeroClass.HUNTER, 3);
		setDescription("For each enemy minion, summon a 1/1 Hound with Charge.");
		setSpell(new UnleashTheHoundsSpell());
		setTargetRequirement(TargetSelection.NONE);
	}

}
