package net.demilich.metastone.game.cards.concrete.tokens.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.UniqueEntity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;

public class StoneclawTotem extends MinionCard {

	public StoneclawTotem() {
		super("Stoneclaw Totem", 0, 2, Rarity.FREE, HeroClass.SHAMAN, 1);
		setDescription("Taunt");
		setRace(Race.TOTEM);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 460;
	}

	@Override
	public Minion summon() {
		Minion stoneclawTotem = createMinion(GameTag.TAUNT);
		stoneclawTotem.setTag(GameTag.UNIQUE_ENTITY, UniqueEntity.STONECLAW_TOTEM);
		return stoneclawTotem;
	}
}
