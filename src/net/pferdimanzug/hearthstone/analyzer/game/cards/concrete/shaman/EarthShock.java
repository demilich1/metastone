package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public class EarthShock extends SpellCard {

	public EarthShock() {
		super("Earth Shock", Rarity.COMMON, HeroClass.SHAMAN, 1);
		setDescription("Silence a minion, then deal $1 damage to it.");
		
	}

}
