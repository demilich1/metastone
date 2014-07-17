package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class LordOfTheArena extends MinionCard {

	public LordOfTheArena() {
		super("Lord of the Arena", 6, 5, Rarity.FREE, HeroClass.ANY, 6);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 157;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
