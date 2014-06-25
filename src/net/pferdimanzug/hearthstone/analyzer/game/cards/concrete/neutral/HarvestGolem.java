package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;

public class HarvestGolem extends MinionCard {

	private class DamagedGolem extends MinionCard {

		public DamagedGolem() {
			super("Damaged Golem", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}

	public HarvestGolem() {
		super("Harvest Golem", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Deathrattle: Summon a 2/1 Damaged Golem.");
	}

	@Override
	public Minion summon() {
		Spell deathrattle = new SummonSpell(new DamagedGolem());
		Minion harvestGolem = createMinion();
		harvestGolem.addDeathrattle(deathrattle);
		return harvestGolem;
	}

}
