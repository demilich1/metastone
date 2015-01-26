package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class AlAkirTheWindlord extends MinionCard {

	public AlAkirTheWindlord() {
		super("Al'Akir the Windlord", 3, 5, Rarity.LEGENDARY, HeroClass.SHAMAN, 8);
		setDescription("Windfury, Charge, Divine Shield, Taunt");
	}

	@Override
	public int getTypeId() {
		return 309;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.WINDFURY, GameTag.CHARGE, GameTag.DIVINE_SHIELD, GameTag.TAUNT);
	}
}
