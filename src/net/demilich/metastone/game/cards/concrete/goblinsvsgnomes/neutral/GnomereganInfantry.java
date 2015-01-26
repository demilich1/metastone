package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class GnomereganInfantry extends MinionCard {

	public GnomereganInfantry() {
		super("Gnomeregan Infantry", 1, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Charge. Taunt");
	}

	@Override
	public int getTypeId() {
		return 518;
	}



	@Override
	public Minion summon() {
		Minion gnomereganInfantry = createMinion(GameTag.CHARGE, GameTag.TAUNT);
		return gnomereganInfantry;
	}
}
