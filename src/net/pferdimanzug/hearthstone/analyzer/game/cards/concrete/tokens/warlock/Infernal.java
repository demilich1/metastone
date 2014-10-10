package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class Infernal extends MinionCard {

	public Infernal() {
		super("Infernal", 6, 6, Rarity.COMMON, HeroClass.WARLOCK, 6);
		setRace(Race.DEMON);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 470;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
