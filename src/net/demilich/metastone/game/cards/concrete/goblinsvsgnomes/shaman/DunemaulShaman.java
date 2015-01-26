package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;

public class DunemaulShaman extends MinionCard {

	public DunemaulShaman() {
		super("Dunemaul Shaman", 5, 4, Rarity.RARE, HeroClass.SHAMAN, 4);
		setDescription("Windfury, Overload: (1) 50% chance to attack the wrong enemy.");
		setTag(GameTag.OVERLOAD, 1);
	}

	@Override
	public int getTypeId() {
		return 577;
	}



	@Override
	public Minion summon() {
		Minion dunemaulShaman = createMinion(GameTag.WINDFURY, GameTag.FUMBLE);
		return dunemaulShaman;
	}
}
