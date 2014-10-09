package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Hound extends MinionCard {

	public Hound() {
		super("Hound", 1, 1, Rarity.FREE, HeroClass.HUNTER, 1);
		setDescription("Charge");
		setCollectible(false);
		setRace(Race.BEAST);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}