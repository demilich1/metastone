package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.cards.CardCatalogue;
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

	public static HeroCard getHeroCard(HeroClass heroClass) {
		switch (heroClass) {
		case DRUID:
			return (HeroCard) CardCatalogue.getCardById("hero_malfurion");
		case HUNTER:
			return (HeroCard) CardCatalogue.getCardById("hero_rexxar");
		case MAGE:
			return (HeroCard) CardCatalogue.getCardById("hero_jaina");
		case PALADIN:
			return (HeroCard) CardCatalogue.getCardById("hero_uther");
		case PRIEST:
			return (HeroCard) CardCatalogue.getCardById("hero_anduin");
		case ROGUE:
			return (HeroCard) CardCatalogue.getCardById("hero_valeera");
		case SHAMAN:
			return (HeroCard) CardCatalogue.getCardById("hero_thrall");
		case WARLOCK:
			return (HeroCard) CardCatalogue.getCardById("hero_guldan");
		case WARRIOR:
			return (HeroCard) CardCatalogue.getCardById("hero_garrosh");
		default:
			break;
		}
		return null;
	}
	
	public MetaHero() {
		super(createDesc());
	}

}
