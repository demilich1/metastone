package net.demilich.metastone.gui.common;

import javafx.util.StringConverter;
import net.demilich.metastone.game.entities.heroes.HeroTemplate;

public class HeroStringConverter extends StringConverter<HeroTemplate> {

	@Override
	public HeroTemplate fromString(String arg0) {
		return null;
	}

	@Override
	public String toString(HeroTemplate hero) {
		return hero.getHeroClass().toString();
	}

}