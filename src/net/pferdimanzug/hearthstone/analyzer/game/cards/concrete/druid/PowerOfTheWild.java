package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseOneCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PowerOfTheWild extends ChooseOneCard {

	private class PowerOfTheWildBuff extends SpellCard {

		protected PowerOfTheWildBuff() {
			super("Power of the Wild (+1/+1)", Rarity.COMMON, HeroClass.DRUID, 2);
			setSpell(new BuffSpell(1, 1));
			setTargetRequirement(TargetSelection.NONE);
			setPredefinedTarget(EntityReference.FRIENDLY_MINIONS);
		}
	}
	
	private class PowerOfTheWildPanther extends MinionCard {
		
		public PowerOfTheWildPanther() {
			super("Panther", 3, 2, Rarity.COMMON, HeroClass.DRUID, 2);
			setTag(GameTag.RACE, Race.BEAST);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}
		
	}
	
	public PowerOfTheWild() {
		super("Power of the Wild", CardType.SPELL, Rarity.COMMON, HeroClass.DRUID, 2);
		setDescription("Choose One - Give your minions +1/+1; or Summon a 3/2 Panther.");
		setCard1(new PowerOfTheWildBuff());
		setCard2(new PowerOfTheWildPanther());
	}

}
