package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.cards.desc.HeroCardDesc;
import net.demilich.metastone.game.cards.desc.HeroPowerCardDesc;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.heroes.powers.HeroPower;

public class NullHeroCard extends HeroCard {
	private HeroClass heroClass;

	public NullHeroCard(HeroClass heroClass) {
		super(getNullHeroCardDesc(heroClass));
		setHeroClass(heroClass);
	}

	public NullHeroCard(HeroCardDesc desc) {
		super(desc);
	}

	private static HeroCardDesc getNullHeroCardDesc(HeroClass forClass) {
		HeroCardDesc heroCardDesc = new HeroCardDesc();
		heroCardDesc.baseManaCost = 0;
		heroCardDesc.name = "Powerless Hero";
		heroCardDesc.type = CardType.HERO;
		heroCardDesc.heroClass = forClass;
		heroCardDesc.attributes.put(Attribute.HP, 30);
		heroCardDesc.attributes.put(Attribute.MAX_HP, 30);

		return heroCardDesc;
	}

	@Override
	public Hero createHero() {
		HeroPowerCardDesc heroPowerCardDesc = getHeroPowerCardDesc();
		HeroPower powerCard = (HeroPower) heroPowerCardDesc.createInstance();

		HeroCardDesc heroCardDesc = getNullHeroCardDesc(getHeroClass());
		HeroCard heroCard = new HeroCard(heroCardDesc);

		Hero hero = new Hero(heroCard, powerCard);
		for (Attribute gameTag : getAttributes().keySet()) {
			if (inheritedAttributes.contains(gameTag)) {
				hero.setAttribute(gameTag, getAttribute(gameTag));
			}
		}

		hero.setRace(heroCardDesc.race);
		return hero;
	}

	private static HeroPowerCardDesc getHeroPowerCardDesc() {
		HeroPowerCardDesc heroPowerCardDesc = new HeroPowerCardDesc();
		heroPowerCardDesc.id = "hero_power_null";
		heroPowerCardDesc.name = "Powerless!";
		heroPowerCardDesc.baseManaCost = 0;
		heroPowerCardDesc.type = CardType.HERO_POWER;
		heroPowerCardDesc.heroClass = HeroClass.ANY;
		heroPowerCardDesc.rarity = Rarity.RARE;
		heroPowerCardDesc.description = "You have no powers!";
		return heroPowerCardDesc;
	}

	private void setHeroClass(HeroClass heroClass) {
		this.heroClass = heroClass;
	}

	public HeroClass getHeroClass() {
		return this.heroClass;
	}
}
