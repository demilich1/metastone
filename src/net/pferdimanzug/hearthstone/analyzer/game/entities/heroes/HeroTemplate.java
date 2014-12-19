package net.pferdimanzug.hearthstone.analyzer.game.entities.heroes;

import javafx.scene.image.Image;
import net.pferdimanzug.hearthstone.analyzer.gui.IconFactory;

public class HeroTemplate {
	
	private final String name;
	private final HeroClass heroClass;

	public HeroTemplate(String name, HeroClass heroClass) {
		this.name = name;
		this.heroClass = heroClass;
	}
	
	public Hero createHero() {
		return HeroFactory.createHero(heroClass);
	}

	public HeroClass getHeroClass() {
		return heroClass;
	}

	public Image getImage() {
		return new Image(IconFactory.getHeroIconUrl(getHeroClass()));
	}
	
	public String getName() {
		return name;
	}

}
