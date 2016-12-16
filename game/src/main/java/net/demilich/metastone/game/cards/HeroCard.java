package net.demilich.metastone.game.cards;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.cards.desc.HeroCardDesc;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.heroes.powers.HeroPower;

public class HeroCard extends Card {
	private static final long serialVersionUID = 1L;

	protected static final Set<Attribute> inheritedAttributes = new HashSet<Attribute>(
			Arrays.asList(new Attribute[]{Attribute.HP, Attribute.MAX_HP, Attribute.BASE_HP, Attribute.ARMOR}));

	private HeroCardDesc desc;

	protected HeroCard() {
		super();
	}

	public HeroCard(HeroCardDesc desc) {
		super(desc);
		setAttribute(Attribute.BASE_HP, getAttributeValue(Attribute.MAX_HP));
		this.desc = desc;
	}

	public Hero createHero() {
		HeroPower heroPower = (HeroPower) CardCatalogue.getCardById(desc.heroPower);
		Hero hero = new Hero(this, heroPower);
		for (Attribute gameTag : getAttributes().keySet()) {
			if (inheritedAttributes.contains(gameTag)) {
				hero.setAttribute(gameTag, getAttribute(gameTag));
			}
		}
		hero.setRace(desc.race);
		return hero;
	}

	@Override
	public PlayCardAction play() {
		throw new UnsupportedOperationException("Hero cards cannot be played");
	}

}
