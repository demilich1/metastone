package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.BattlecrySummonMinion;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

public class RazorfenHunter extends MinionCard {

	public RazorfenHunter() {
		super("Razorfen Hunter", Rarity.FREE, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion razorfenHunter = createMinion(2, 3);
		razorfenHunter.setTag(GameTag.BATTLECRY, new BattlecrySummonMinion(new Boar()));
		return razorfenHunter;
	}

	private class Boar extends MinionCard {

		public Boar() {
			super("Boar", Rarity.FREE, HeroClass.ANY, 1);
		}

		@Override
		public Minion summon() {
			return createMinion(1, 1, Race.BEAST);
		}

	}

}
