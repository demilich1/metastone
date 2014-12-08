package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueEntity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

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
