package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomMinionPredicateSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class PilotedShredder extends MinionCard {

	public PilotedShredder() {
		super("Piloted Shredder", 4, 3, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Deathrattle: Summon a random 2-cost minion.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 538;
	}



	@Override
	public Minion summon() {
		Minion pilotedShredder = createMinion();
		SpellDesc summonRandom = SummonRandomMinionPredicateSpell.create(card -> card.getBaseManaCost() == 2);
		pilotedShredder.addDeathrattle(summonRandom);
		return pilotedShredder;
	}
}
