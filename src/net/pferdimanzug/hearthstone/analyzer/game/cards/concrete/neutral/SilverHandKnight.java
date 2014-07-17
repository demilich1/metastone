package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;

public class SilverHandKnight extends MinionCard {

	private class Squire extends MinionCard {

		public Squire() {
			super("Squire", 2, 2, Rarity.COMMON, HeroClass.ANY, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}
		
	}

	public SilverHandKnight() {
		super("Silver Hand Knight", 4, 4, Rarity.COMMON, HeroClass.ANY, 5);
		setDescription("Battlecry: Summon a 2/2 Squire.");
	}
	
	@Override
	public int getTypeId() {
		return 199;
	}



	@Override
	public Minion summon() {
		Minion silverHandKnight = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new SummonSpell(new Squire()));
		battlecry.setResolvedLate(true);
		silverHandKnight.setBattlecry(battlecry);
		return silverHandKnight;
	}
}
