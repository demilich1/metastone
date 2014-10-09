package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Hyena extends MinionCard {

	public Hyena() {
		super("Hyena", 2, 2, Rarity.RARE, HeroClass.HUNTER, 2);
		setCollectible(false);
		setRace(Race.BEAST);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
	
}