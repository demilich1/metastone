package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.rogue;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

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
