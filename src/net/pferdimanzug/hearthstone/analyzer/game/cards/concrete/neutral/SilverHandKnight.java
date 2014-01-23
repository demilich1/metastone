package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;

public class SilverHandKnight extends MinionCard {

	public SilverHandKnight() {
		super("Silver Hand Knight", Rarity.COMMON, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		Battlecry battlecry = Battlecry.createBattlecry(new SummonSpell(new Squire()));
		Minion silverHandKnight = createMinion(4, 4);
		silverHandKnight.setTag(GameTag.BATTLECRY, battlecry);
		return silverHandKnight;
	}
	
	private class Squire extends MinionCard {

		public Squire() {
			super("Squire", Rarity.COMMON, HeroClass.ANY, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(2, 2);
		}
		
	}

}
