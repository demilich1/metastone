package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class MirrorImageToken extends MinionCard {

	public MirrorImageToken() {
		super("Mirror Image", 0, 2, Rarity.FREE, HeroClass.MAGE, 0);
		setDescription("Taunt");
		setCollectible(false);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
	
}