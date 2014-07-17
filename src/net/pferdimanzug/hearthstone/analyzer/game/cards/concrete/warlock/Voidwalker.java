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
		setDescription("Taunt");
		setRace(Race.DEMON);
	}

	@Override
	public int getTypeId() {
		return 358;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
