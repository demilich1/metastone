package net.demilich.metastone.game.cards.concrete.tokens.warlock;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

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



	@Override
	public int getTypeId() {
		return 613;
	}
}
