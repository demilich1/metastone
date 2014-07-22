package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class PoisonSeeds extends SpellCard {

	public PoisonSeeds() {
		super("Poison Seeds", Rarity.COMMON, HeroClass.DRUID, 4);
		setDescription("Destroy all minions and summon 2/2 treants to replace them");

		setSpell(new MetaSpell(new DestroySpell(), new SummonSpell(new Treant())));
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(EntityReference.ALL_MINIONS);
	}

	private class Treant extends MinionCard {

		public Treant() {
			super("Treant", 2, 2, Rarity.FREE, HeroClass.ANY, 2);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}
}
