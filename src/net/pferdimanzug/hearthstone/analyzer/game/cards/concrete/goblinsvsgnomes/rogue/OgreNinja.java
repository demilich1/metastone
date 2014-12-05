package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class OgreNinja extends MinionCard {

	public OgreNinja() {
		super("Ogre Ninja", 6, 6, Rarity.RARE, HeroClass.ROGUE, 5);
		setDescription("Stealth. 50% chance to attack the wrong enemy.");
	}

	@Override
	public int getTypeId() {
		return 570;
	}



	@Override
	public Minion summon() {
		Minion ogreNinja = createMinion(GameTag.STEALTHED, GameTag.FUMBLE);
		return ogreNinja;
	}
}
