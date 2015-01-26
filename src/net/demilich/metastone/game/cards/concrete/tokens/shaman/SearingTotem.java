package net.demilich.metastone.game.cards.concrete.tokens.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class SearingTotem extends MinionCard {

	public SearingTotem() {
		super("Searing Totem", 1, 1, Rarity.FREE, HeroClass.SHAMAN, 1);
		setRace(Race.TOTEM);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 458;
	}

	@Override
	public Minion summon() {
		Minion searingTotem = createMinion();
		searingTotem.setTag(GameTag.UNIQUE_ENTITY, UniqueEntity.SEARING_TOTEM);
		return searingTotem;
	}
}
