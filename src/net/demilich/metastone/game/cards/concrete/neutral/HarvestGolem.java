package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.DamagedGolem;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class HarvestGolem extends MinionCard {

	public HarvestGolem() {
		super("Harvest Golem", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Deathrattle: Summon a 2/1 Damaged Golem.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 140;
	}

	@Override
	public Minion summon() {
		SpellDesc deathrattle = SummonSpell.create(new DamagedGolem());
		Minion harvestGolem = createMinion();
		harvestGolem.addDeathrattle(deathrattle);
		return harvestGolem;
	}
}
