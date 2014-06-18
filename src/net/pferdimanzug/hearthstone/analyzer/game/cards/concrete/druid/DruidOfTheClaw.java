package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseOneCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class DruidOfTheClaw extends ChooseOneCard {

	private class BearForm extends MinionCard {

		public BearForm() {
			super("Druid of the Claw (Taunt)", 4, 6, Rarity.COMMON, HeroClass.DRUID, 5);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.TAUNT);
		}
		
	}
	
	private class CatForm extends MinionCard {

		public CatForm() {
			super("Druid of the Claw (Charge)", 4, 4, Rarity.COMMON, HeroClass.DRUID, 5);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.CHARGE);
		}
		
	}
	
	public DruidOfTheClaw() {
		super("Druid of the Claw", CardType.MINION, Rarity.COMMON, HeroClass.DRUID, 5);
		setDescription("Choose One - Charge; or +2 Health and Taunt.");
		setCard1(new BearForm());
		setCard2(new CatForm());
	}

}
