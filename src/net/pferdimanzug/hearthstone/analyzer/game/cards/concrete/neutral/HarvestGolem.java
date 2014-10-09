package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral.DamagedGolem;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class HarvestGolem extends MinionCard {

	public HarvestGolem() {
		super("Harvest Golem", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Deathrattle: Summon a 2/1 Damaged Golem.");
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
