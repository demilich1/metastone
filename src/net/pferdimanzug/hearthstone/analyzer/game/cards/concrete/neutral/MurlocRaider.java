package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class MurlocRaider extends MinionCard {

	public MurlocRaider() {
		super("Murloc Raider", 2, 1, Rarity.FREE, HeroClass.ANY, 1);
		setRace(Race.MURLOC);
	}

	@Override
	public int getTypeId() {
		return 170;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
