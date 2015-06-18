package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonRandomMinionFilteredSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class PilotedSkyGolem extends MinionCard {

	public PilotedSkyGolem() {
		super("Piloted Sky Golem", 6, 4, Rarity.EPIC, HeroClass.ANY, 6);
		setDescription("Deathrattle: Summon a random 4-Cost minion.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 539;
	}

	@Override
	public Minion summon() {
		Minion pilotedSkyGolem = createMinion();
		SpellDesc summonRandom = SummonRandomMinionFilteredSpell.create(card -> card.getBaseManaCost() == 4);
		pilotedSkyGolem.addDeathrattle(summonRandom);
		return pilotedSkyGolem;
	}
}
