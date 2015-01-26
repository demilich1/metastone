package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class ArgentSquire extends MinionCard {

	public ArgentSquire() {
		super("Argent Squire", 1, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Divine Shield");
	}

	@Override
	public int getTypeId() {
		return 90;
	}



	@Override
	public Minion summon() {
		Minion argentSquire = createMinion(GameTag.DIVINE_SHIELD);
		return argentSquire;
	}
}
