package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Hyena extends MinionCard {

	public Hyena() {
		super("Hyena", 2, 2, Rarity.RARE, HeroClass.HUNTER, 2);
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 422;
	}
	
	@Override
	public Minion summon() {
		return createMinion();
	}
}
