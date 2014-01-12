package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Reinforce extends HeroPower {

	private class SilverHandRecruit extends MinionCard {

		public SilverHandRecruit() {
			super("SilverHand Recruit", Rarity.FREE, HeroClass.PALADIN, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(1, 1);
		}

	}

	public Reinforce() {
		super("Reinforce");
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new SummonSpell(new SilverHandRecruit()));
	}

}
