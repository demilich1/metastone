package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class MogushanWarden extends MinionCard {

	public MogushanWarden() {
		super("Mogu'shan Warden", 1, 7, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Taunt");
	}

	@Override
	public int getTypeId() {
		return 167;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
