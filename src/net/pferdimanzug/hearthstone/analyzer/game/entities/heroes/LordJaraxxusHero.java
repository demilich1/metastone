package net.pferdimanzug.hearthstone.analyzer.game.entities.heroes;

import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.Inferno;

public class LordJaraxxusHero extends Hero {

	public LordJaraxxusHero() {
		super("Lord Jaraxxus", HeroClass.WARLOCK, new Inferno());
		setRace(Race.DEMON);
		
		setMaxHp(15);
		setHp(15);
	}

}