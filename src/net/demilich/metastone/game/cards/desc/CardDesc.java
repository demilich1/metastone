package net.demilich.metastone.game.cards.desc;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.heroes.HeroClass;

public abstract class CardDesc {
	
	public String id;
	public String name;
	public String description;
	public CardType type;
	public HeroClass heroClass;
	public int baseManaCost;

}
