package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class TundraRhino extends MinionCard {

	public TundraRhino() {
		super("Tundra Rhino", 2, 5, Rarity.FREE, HeroClass.HUNTER, 5);
		setDescription("Your Beasts have Charge.");
		setRace(Race.BEAST);
	}
	
	@Override
	public int getTypeId() {
		return 49;
	}



	@Override
	public Minion summon() {
		Minion tundraRhino = createMinion();
		return tundraRhino;
	}
}
