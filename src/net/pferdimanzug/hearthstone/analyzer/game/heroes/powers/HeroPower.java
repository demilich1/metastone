package net.pferdimanzug.hearthstone.analyzer.game.heroes.powers;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;

public abstract class HeroPower extends Card {
	
	private boolean used;
	
	public HeroPower(String name) {
		super(name, CardType.HERO_POWER, Rarity.FREE, HeroClass.ANY, 2);
	}

	public boolean hasBeenUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

}
