package net.demilich.metastone.game.cards.concrete.tokens.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class Hound extends MinionCard {

	public Hound() {
		super("Hound", 1, 1, Rarity.FREE, HeroClass.HUNTER, 1);
		setDescription("Charge");
		setRace(Race.BEAST);
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 420;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
