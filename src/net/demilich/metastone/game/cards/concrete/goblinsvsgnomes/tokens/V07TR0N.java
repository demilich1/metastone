package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class V07TR0N extends MinionCard {

	public V07TR0N() {
		super("V-07-TR-0N", 4, 8, Rarity.LEGENDARY, HeroClass.ANY, 8);
		setDescription("Charge. Mega-Windfury (Can attack four times a turn.)");
		setRace(Race.MECH);
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 594;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE, GameTag.MEGA_WINDFURY);
	}
}
