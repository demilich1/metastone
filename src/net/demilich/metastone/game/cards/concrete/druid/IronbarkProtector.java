package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class IronbarkProtector extends MinionCard {

	public IronbarkProtector() {
		super("Ironbark Protector", 8, 8, Rarity.FREE, HeroClass.DRUID, 8);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 10;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
