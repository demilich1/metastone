package net.demilich.metastone.game.entities.heroes;

import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.heroes.powers.Inferno;

public class LordJaraxxusHero extends Hero {
	
	public LordJaraxxusHero() {
		super("Lord Jaraxxus", HeroClass.WARLOCK, new Inferno());
		setRace(Race.DEMON);
		
		setMaxHp(15);
		setHp(15);
	}

}