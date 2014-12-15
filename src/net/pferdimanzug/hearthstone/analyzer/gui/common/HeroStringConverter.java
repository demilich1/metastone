package net.pferdimanzug.hearthstone.analyzer.gui.common;

import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroTemplate;
import javafx.util.StringConverter;

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