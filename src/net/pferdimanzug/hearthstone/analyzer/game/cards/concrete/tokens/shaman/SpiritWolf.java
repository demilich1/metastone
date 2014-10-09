package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class SpiritWolf extends MinionCard {

	public SpiritWolf() {
		super("Spirit Wolf", 2, 3, Rarity.RARE, HeroClass.SHAMAN, 2);
		setDescription("Taunt");
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}

}