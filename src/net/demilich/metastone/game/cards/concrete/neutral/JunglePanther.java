package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class JunglePanther extends MinionCard {

	public JunglePanther() {
		super("Jungle Panther", 4, 2, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Stealth");
	}

	@Override
	public int getTypeId() {
		return 149;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.STEALTH);
	}
}
