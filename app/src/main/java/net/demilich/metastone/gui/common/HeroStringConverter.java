package net.demilich.metastone.gui.common;

import javafx.util.StringConverter;
import net.demilich.metastone.game.cards.HeroCard;

public class HeroStringConverter extends StringConverter<HeroCard> {

	@Override
	public HeroCard fromString(String arg0) {
		return null;
	}

	@Override
	public String toString(HeroCard hero) {
		return hero.getHeroClass().toString();
	}

}