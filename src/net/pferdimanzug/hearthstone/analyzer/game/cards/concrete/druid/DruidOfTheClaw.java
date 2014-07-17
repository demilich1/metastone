package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseBattlecryCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DruidOfTheClaw extends ChooseBattlecryCard {

	private class BearForm extends MinionCard {

		public BearForm() {
			super("Druid of the Claw", 4, 6, Rarity.COMMON, HeroClass.DRUID, 5);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.TAUNT);
		}

	}

	private class CatForm extends MinionCard {

		public CatForm() {
			super("Druid of the Claw", 4, 4, Rarity.COMMON, HeroClass.DRUID, 5);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.CHARGE);
		}

	}

	public DruidOfTheClaw() {
		super("Druid of the Claw", 4, 4, Rarity.COMMON, HeroClass.DRUID, 5);
		setDescription("Choose One - Charge; or +2 Health and Taunt.");
	}

	@Override
	protected String getAction1Suffix() {
		return "Charge";
	}

	@Override
	protected String getAction2Suffix() {
		return "Taunt";
	}

	@Override
	protected Battlecry getBattlecry1() {
		Spell transformSpell = new TransformMinionSpell(new CatForm());
		transformSpell.setTarget(EntityReference.SELF);
		return Battlecry.createBattlecry(transformSpell);
	}

	@Override
	protected Battlecry getBattlecry2() {
		Spell transformSpell = new TransformMinionSpell(new BearForm());
		transformSpell.setTarget(EntityReference.SELF);
		return Battlecry.createBattlecry(transformSpell);
	}

	@Override
	public int getTypeId() {
		return 6;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
