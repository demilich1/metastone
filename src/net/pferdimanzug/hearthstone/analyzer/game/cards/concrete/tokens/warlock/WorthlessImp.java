package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class WorthlessImp extends MinionCard {

	public WorthlessImp() {
		super("Worthless Imp", 1, 1, Rarity.COMMON, HeroClass.WARLOCK, 1);
		setDescription("You are out of demons! At least there are always imps...");
		setRace(Race.DEMON);
	}

	@Override
	public Minion summon() {
		return createMinion();
	}

}