package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

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
