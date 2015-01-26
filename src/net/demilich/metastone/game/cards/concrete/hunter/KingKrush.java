package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class KingKrush extends MinionCard {

	public KingKrush() {
		super("King Krush", 8, 8, Rarity.LEGENDARY, HeroClass.HUNTER, 9);
		setDescription("Charge");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 39;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
