package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Misha extends MinionCard {

	public Misha() {
		super("Misha", 4, 4, Rarity.FREE, HeroClass.HUNTER, 3);
		setRace(Race.BEAST);
		setDescription("Taunt");
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
	
}