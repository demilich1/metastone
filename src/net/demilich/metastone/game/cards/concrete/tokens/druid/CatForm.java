package net.demilich.metastone.game.cards.concrete.tokens.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class CatForm extends MinionCard {

	public CatForm() {
		super("Druid of the Claw (Cat)", 4, 4, Rarity.COMMON, HeroClass.DRUID, 5);
		setRace(Race.BEAST);
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 416;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
