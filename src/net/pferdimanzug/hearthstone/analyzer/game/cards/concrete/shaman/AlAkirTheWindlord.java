package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

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
