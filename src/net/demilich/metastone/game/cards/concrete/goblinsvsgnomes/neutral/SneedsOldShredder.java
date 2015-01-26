package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonRandomMinionPredicateSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SneedsOldShredder extends MinionCard {

	public SneedsOldShredder() {
		super("Sneed's Old Shredder", 5, 7, Rarity.LEGENDARY, HeroClass.ANY, 8);
		setDescription("Deathrattle: Summon a random legendary minion.");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 544;
	}



	@Override
	public Minion summon() {
		Minion sneedsOldShredder = createMinion();
		SpellDesc deathrattle = SummonRandomMinionPredicateSpell.create(card -> card.getRarity() == Rarity.LEGENDARY);
		sneedsOldShredder.addDeathrattle(deathrattle);
		return sneedsOldShredder;
	}
}
