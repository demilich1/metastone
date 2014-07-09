package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformRandomMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class GelbinMekkatorque extends MinionCard {

	public GelbinMekkatorque() {
		super("Gelbin Mekkatorque", 6, 6, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Battlecry: Summon an AWESOME invention.");
	}

	@Override
	public Minion summon() {
		Minion gelbinMekkatorque = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new SummonRandomSpell(new Emboldener3000(), new HomingChicken(), new Poultryizer(),
				new RepairBot()));
		gelbinMekkatorque.setBattlecry(battlecry);
		return gelbinMekkatorque;
	}

	private class Emboldener3000 extends MinionCard {

		public Emboldener3000() {
			super("Emboldener 3000", 0, 4, Rarity.FREE, HeroClass.ANY, 1);
			setDescription("At the end of your turn, give a random minion +1/+1.");
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion emboldener3000 = createMinion();
			Spell buffSpell = new BuffRandomSpell(1, 1);
			buffSpell.setTarget(EntityReference.ALL_MINIONS);
			SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), buffSpell);
			emboldener3000.setSpellTrigger(trigger);
			return emboldener3000;
		}

	}

	private class HomingChicken extends MinionCard {

		public HomingChicken() {
			super("Homing Chicken", 0, 1, Rarity.FREE, HeroClass.ANY, 1);
			setDescription("At the start of your turn, destroy this minion and draw 3 cards.");
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion homingChicken = createMinion();
			Spell destroySpell = new DestroySpell();
			destroySpell.setTarget(EntityReference.SELF);
			Spell homingChickenSpell = new MetaSpell(new DrawCardSpell(3), destroySpell);
			SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), homingChickenSpell);
			homingChicken.setSpellTrigger(trigger);
			return homingChicken;
		}
	}

	private class Poultryizer extends MinionCard {

		public Poultryizer() {
			super("Poultryizer", 0, 3, Rarity.FREE, HeroClass.ANY, 1);
			setDescription("At the start of your turn, transform a random minion into a 1/1 Chicken.");
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion poultryizer = createMinion();
			Spell chickenizeSpell = new TransformRandomMinionSpell(new Chicken());
			chickenizeSpell.setTarget(EntityReference.ALL_MINIONS);
			SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), chickenizeSpell);
			poultryizer.setSpellTrigger(trigger);
			return poultryizer;
		}

		private class Chicken extends MinionCard {

			public Chicken() {
				super("Chicken", 1, 1, Rarity.FREE, HeroClass.ANY, 0);
				setDescription("Hey Chicken!");
				setRace(Race.BEAST);
				setCollectible(false);
			}

			@Override
			public Minion summon() {
				return createMinion();
			}

		}

	}

	private class RepairBot extends MinionCard {

		public RepairBot() {
			super("Repair Bot", 0, 3, Rarity.FREE, HeroClass.ANY, 1);
			setDescription("At the end of your turn, restore 6 Health to a damaged character.");
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion repairBot = createMinion();
			Spell healRandomSpell = new HealRandomSpell(6);
			healRandomSpell.setTarget(EntityReference.ALL_CHARACTERS);
			SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), healRandomSpell);
			repairBot.setSpellTrigger(trigger);
			return repairBot;
		}

	}

}
