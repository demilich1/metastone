package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Nozdormu extends MinionCard {

	public Nozdormu() {
		super("Nozdormu", 8, 8, Rarity.LEGENDARY, HeroClass.ANY, 9);
		// please note: this mechanic is not actually implemented,
		// as it is impossible to quantify and in high-level of play
		// basically irrelevant
		setDescription("Players only have 15 seconds to take their turns.");
		setRace(Race.DRAGON);
	}

	@Override
	public int getTypeId() {
		return 177;
	}



	@Override
	public Minion summon() {
		return createMinion();
	}
}
