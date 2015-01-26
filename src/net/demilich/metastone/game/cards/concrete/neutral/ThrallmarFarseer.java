package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class ThrallmarFarseer extends MinionCard {

	public ThrallmarFarseer() {
		super("Thrallmar Farseer", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Windfury");
	}

	@Override
	public int getTypeId() {
		return 218;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.WINDFURY);
	}
}
