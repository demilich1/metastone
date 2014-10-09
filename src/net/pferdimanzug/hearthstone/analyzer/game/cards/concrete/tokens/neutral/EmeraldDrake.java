package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class EmeraldDrake extends MinionCard {

	public EmeraldDrake() {
		super("Emerald Drake", 7, 6, Rarity.FREE, HeroClass.ANY, 4);
		setRace(Race.DRAGON);

		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

	@Override
	public int getTypeId() {
		return 438;
	}
}
