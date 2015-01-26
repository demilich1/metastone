package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class ForceTankMax extends MinionCard {

	public ForceTankMax() {
		super("Force-Tank MAX", 7, 7, Rarity.COMMON, HeroClass.ANY, 8);
		setDescription("Divine Shield");
		setRace(Race.MECH);
	}

	@Override
	public int getTypeId() {
		return 515;
	}



	@Override
	public Minion summon() {
		Minion forceTankMax = createMinion(GameTag.DIVINE_SHIELD);
		return forceTankMax;
	}
}
