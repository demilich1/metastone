package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonRandomMinionFilteredSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
		SpellDesc summonRandom = SummonRandomMinionFilteredSpell.create(card -> card.getBaseManaCost() == 2);
		pilotedShredder.addDeathrattle(summonRandom);
		return pilotedShredder;
	}
}
