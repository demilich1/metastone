package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ForceOfNature extends SpellCard {

	public ForceOfNature() {
		super("Force of Nature", Rarity.EPIC, HeroClass.DRUID, 6);
		setDescription("Summon three 2/2 Treants with Charge that die at the end of the turn.");

		//TODO: check if this card can be played when board is full
		setSpell(SummonSpell.create(new Treant(), new Treant(), new Treant()));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 7;
	}

	private class Treant extends MinionCard {

		public Treant() {
			super("Treant", 2, 2, Rarity.COMMON, HeroClass.DRUID, 1);
			setDescription("Charge. At the end of the turn, destroy this minion.");
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			Minion treant = createMinion(GameTag.CHARGE);
			SpellDesc destroySpell = DestroySpell.create();
			destroySpell.setTarget(EntityReference.SELF);
			SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), destroySpell);
			treant.setSpellTrigger(trigger);
			return treant;
		}

	}
}
