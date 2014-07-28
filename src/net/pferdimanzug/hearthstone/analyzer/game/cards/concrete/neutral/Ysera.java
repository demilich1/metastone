package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueMinion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveRandomCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Ysera extends MinionCard {

	private class Dream extends SpellCard {

		public Dream() {
			super("Dream", Rarity.FREE, HeroClass.ANY, 0);
			setDescription("Return a minion to its owners's hand.");
			setCollectible(false);

			setSpell(new ReturnMinionToHandSpell());
			setTargetRequirement(TargetSelection.MINIONS);
		}

	}

	private class EmeraldDrake extends MinionCard {

		public EmeraldDrake() {
			super("Emerald Drake", 7, 6, Rarity.FREE, HeroClass.ANY, 4);
			setCollectible(false);

			setRace(Race.DRAGON);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}

	private class LaughingSister extends MinionCard {

		public LaughingSister() {
			super("Laughing Sister", 3, 5, Rarity.FREE, HeroClass.ANY, 3);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion(GameTag.UNTARGETABLE_BY_SPELLS);
		}
	}

	private class Nightmare extends SpellCard {

		public Nightmare() {
			super("Nightmare", Rarity.FREE, HeroClass.ANY, 0);
			setDescription("Give a minion +5/+5. At the start of your next turn, destroy it.");
			Spell destroySpell = new DestroySpell();
			destroySpell.setTarget(EntityReference.SELF);
			SpellTrigger trigger = new SpellTrigger(new TurnStartTrigger(), destroySpell);
			setSpell(new MetaSpell(new BuffSpell(5, 5), new AddSpellTriggerSpell(trigger)));
			setTargetRequirement(TargetSelection.MINIONS);
		}
	}

	private class YseraAwakens extends SpellCard {

		public YseraAwakens() {
			super("Ysera Awakens", Rarity.FREE, HeroClass.ANY, 2);
			setDescription("Deal 5 damage to all characters except Ysera.");

			Spell yseraAwakens = new DamageSpell(
					(context, player, target) -> target.getTag(GameTag.UNIQUE_MINION) == UniqueMinion.YSERA ? 0 : 5);
			yseraAwakens.setTarget(EntityReference.ALL_CHARACTERS);
			setSpell(yseraAwakens);
			setTargetRequirement(TargetSelection.NONE);
		}

	}

	public Ysera() {
		super("Ysera", 4, 12, Rarity.LEGENDARY, HeroClass.ANY, 9);
		setDescription("At the end of your turn, draw a Dream Card.");
		setRace(Race.DRAGON);
	}

	@Override
	public int getTypeId() {
		return 233;
	}

	@Override
	public Minion summon() {
		Minion ysera = createMinion();
		ysera.setTag(GameTag.UNIQUE_MINION, UniqueMinion.YSERA);
		Spell receiveDreamCard = new ReceiveRandomCardSpell(TargetPlayer.SELF, new Dream(), new EmeraldDrake(), new LaughingSister(),
				new Nightmare(), new YseraAwakens());
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), receiveDreamCard);
		ysera.setSpellTrigger(trigger);
		return ysera;
	}
}
