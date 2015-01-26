package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class WhirlingZapOmatic extends MinionCard {

	public WhirlingZapOmatic() {
		super("Whirling Zap-o-matic", 3, 2, Rarity.COMMON, HeroClass.SHAMAN, 2);
		setDescription("Windfury");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 582;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.WINDFURY);
	}
}
