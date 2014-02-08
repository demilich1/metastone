package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Voidwalker extends MinionCard {

	public Voidwalker() {
		super("Voidwalker", 1, 3, Rarity.FREE, HeroClass.WARLOCK, 1);
	}

	@Override
	public Minion summon() {
		return createMinion(Race.DEMON, GameTag.TAUNT);
	}

}
