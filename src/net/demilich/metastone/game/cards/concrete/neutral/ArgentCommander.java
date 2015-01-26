package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class ArgentCommander extends MinionCard {

	public ArgentCommander() {
		super("Argent Commander", 4, 2, Rarity.RARE, HeroClass.ANY, 6);
		setDescription("Charge, Divine Shield");
	}

	@Override
	public int getTypeId() {
		return 89;
	}



	@Override
	public Minion summon() {
		Minion argentCommander = createMinion(GameTag.CHARGE, GameTag.DIVINE_SHIELD);
		return argentCommander;
	}
}
