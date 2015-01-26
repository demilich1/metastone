package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.ChooseOneCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.tokens.druid.Panther;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PowerOfTheWild extends ChooseOneCard {

	public PowerOfTheWild() {
		super("Power of the Wild", CardType.SPELL, Rarity.COMMON, HeroClass.DRUID, 2);
		setDescription("Choose One - Give your minions +1/+1; or Summon a 3/2 Panther.");
		setCard1(new PowerOfTheWildBuff());
		setCard2(new PowerOfTheWildPanther());
	}

	@Override
	public int getTypeId() {
		return 17;
	}

	private class PowerOfTheWildBuff extends SpellCard {

		protected PowerOfTheWildBuff() {
			super("Power of the Wild (+1/+1)", Rarity.COMMON, HeroClass.DRUID, 2);
			setDescription("Give your minions +1/+1");

			setSpell(BuffSpell.create(1, 1));
			setTargetRequirement(TargetSelection.NONE);
			setPredefinedTarget(EntityReference.FRIENDLY_MINIONS);
		}

	}

	private class PowerOfTheWildPanther extends SpellCard {

		public PowerOfTheWildPanther() {
			super("Power of the Wild (Summon Panther)", Rarity.COMMON, HeroClass.DRUID, 2);
			setDescription("Summon a 3/2 Panther.");

			setSpell(SummonSpell.create(new Panther()));
			setTargetRequirement(TargetSelection.NONE);
		}

	}
}
