package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class LaughingSister extends MinionCard {

	public LaughingSister() {
		super("Laughing Sister", 3, 5, Rarity.FREE, HeroClass.ANY, 3);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 444;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.UNTARGETABLE_BY_SPELLS);
	}
}
