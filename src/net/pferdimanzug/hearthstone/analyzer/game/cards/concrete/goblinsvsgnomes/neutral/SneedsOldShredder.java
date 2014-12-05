package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomMinionPredicateSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
