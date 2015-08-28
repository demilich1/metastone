package net.demilich.metastone.game.cards;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;

import javafx.scene.image.Image;
import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.cards.desc.HeroCardDesc;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.gui.IconFactory;

public class HeroCard extends Card {

	private static final Set<Attribute> inheritedAttributes = new HashSet<Attribute>(
			Arrays.asList(new Attribute[] { Attribute.HP, Attribute.MAX_HP, Attribute.ARMOR }));

	private final HeroCardDesc desc;

	public HeroCard(HeroCardDesc desc) {
		super(desc);
		this.desc = desc;
	}

	public Hero createHero() {
		HeroPower heroPower = (HeroPower) CardCatalogue.getCardById(desc.heroPower);
		Hero hero = new Hero(getName(), getClassRestriction(), heroPower);
		for (Attribute gameTag : getAttributes().keySet()) {
			if (inheritedAttributes.contains(gameTag)) {
				hero.setAttribute(gameTag, getAttribute(gameTag));
			}
		}
		hero.setRace(desc.race);
		return hero;
	}

	public HeroClass getHeroClass() {
		return getClassRestriction();
	}

	public Image getImage() {
		return new Image(IconFactory.getHeroIconUrl(getHeroClass()));
	}

	@Override
	public PlayCardAction play() {
		throw new NotImplementedException("Hero cards cannot be played");
	}

}
