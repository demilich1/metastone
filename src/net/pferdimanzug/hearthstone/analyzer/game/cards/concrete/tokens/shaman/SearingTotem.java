package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.UniqueMinion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

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
		searingTotem.setTag(GameTag.UNIQUE_MINION, UniqueMinion.SEARING_TOTEM);
		return searingTotem;
	}
}
