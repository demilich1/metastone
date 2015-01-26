package net.demilich.metastone.game.cards.concrete.tokens.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class MirrorImageToken extends MinionCard {

	public MirrorImageToken() {
		super("Mirror Image", 0, 2, Rarity.FREE, HeroClass.MAGE, 0);
		setDescription("Taunt");
		
		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 426;
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
