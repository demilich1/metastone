package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class OgreBrute extends MinionCard {

	public OgreBrute() {
		super("Ogre Brute", 4, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("50% chance to attack the wrong enemy.");
	}

	@Override
	public int getTypeId() {
		return 537;
	}



	@Override
	public Minion summon() {
		Minion ogreBrute = createMinion(GameTag.FUMBLE);
		return ogreBrute;
	}
}
