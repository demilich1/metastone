package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class MurlocScout extends MinionCard {

	public MurlocScout() {
		super("Murloc Scout", 1, 1, Rarity.FREE, HeroClass.ANY, 0);
		setRace(Race.MURLOC);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 447;
	}

	@Override
	public Minion summon() {
		return createMinion();
	}
}
