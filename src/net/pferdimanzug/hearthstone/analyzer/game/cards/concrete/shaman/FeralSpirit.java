package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FeralSpirit extends SpellCard {

	public FeralSpirit() {
		super("Feral Spirit", Rarity.RARE, HeroClass.SHAMAN, 3);
		setDescription("Summon two 2/3 Spirit Wolves with Taunt. Overload: (2)");

		setSpell(new SummonSpell(new SpiritWolf(), new SpiritWolf()));
		setTargetRequirement(TargetSelection.NONE);
	}

	private class SpiritWolf extends MinionCard {

		public SpiritWolf() {
			super("Spirit Wolf", 2, 3, Rarity.RARE, HeroClass.SHAMAN, 2);
			setDescription("Taunt");
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.TAUNT);
		}

	}

}
