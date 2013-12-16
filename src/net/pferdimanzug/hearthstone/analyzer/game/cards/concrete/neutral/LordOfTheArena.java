package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class LordOfTheArena extends MinionCard {

	public LordOfTheArena() {
		super("Lord of the Arena", Rarity.FREE, HeroClass.ANY, 6);
	}

	@Override
	public Minion summon() {
		return createMinion(6, 5, GameTag.TAUNT);
	}

}
