package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Huffer extends MinionCard {

	public Huffer() {
		super("Huffer", 4, 2, Rarity.FREE, HeroClass.HUNTER, 3);
		setDescription("Charge");
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}

	@Override
	public int getTypeId() {
		return 421;
	}
}
