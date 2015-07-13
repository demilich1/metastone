package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.cards.HeroCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.desc.HeroCardDesc;

public class MetaHero extends HeroCard {
	
	private static HeroCardDesc createDesc() {
		HeroCardDesc desc = new HeroCardDesc();
		desc.collectible = false;
		desc.heroClass = HeroClass.DECK_COLLECTION;
		desc.name = "Depending on deck";
		desc.id = "hero_meta";
		desc.rarity = Rarity.FREE;
		return desc;
	}

	public MetaHero() {
		super(createDesc());
	}

}
