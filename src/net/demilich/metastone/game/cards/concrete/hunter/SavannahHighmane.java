package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.hunter.Hyena;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonSpell;

public class SavannahHighmane extends MinionCard {

	public SavannahHighmane() {
		super("Savannah Highmane", 6, 5, Rarity.RARE, HeroClass.HUNTER, 6);
		setDescription("Deathrattle: Summon two 2/2 Hyenas.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 42;
	}
	
	@Override
	public Minion summon() {
		Minion savannahHighmane = createMinion();
		savannahHighmane.addDeathrattle(SummonSpell.create(new Hyena(), new Hyena()));
		return savannahHighmane;
	}
}
