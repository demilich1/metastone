package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomMinionPredicateSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class PilotedSkyGolem extends MinionCard {

	public PilotedSkyGolem() {
		super("Piloted Sky Golem", 6, 4, Rarity.EPIC, HeroClass.ANY, 6);
		setDescription("Deathrattle: Summon a random 4-Cost minion.");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion pilotedSkyGolem = createMinion();
		SpellDesc summonRandom = SummonRandomMinionPredicateSpell.create(card -> card.getBaseManaCost() == 4);
		pilotedSkyGolem.addDeathrattle(summonRandom);
		return pilotedSkyGolem;
	}

}
