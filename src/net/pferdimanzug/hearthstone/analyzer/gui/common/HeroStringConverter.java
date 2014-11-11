package net.pferdimanzug.hearthstone.analyzer.gui.common;

import javafx.util.StringConverter;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;

public class HeroStringConverter extends StringConverter<Hero> {

	@Override
	public Hero fromString(String arg0) {
		return null;
	}

	@Override
	public String toString(Hero hero) {
		return hero.getHeroClass().toString();
	}

}