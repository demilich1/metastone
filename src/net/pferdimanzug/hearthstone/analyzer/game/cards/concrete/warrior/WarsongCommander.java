package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class WarsongCommander extends MinionCard {

	public WarsongCommander() {
		super("Warsong Commander", 2, 3, Rarity.FREE, HeroClass.WARRIOR, 3);
		setDescription("Whenever you summon a minion with 3 or less Attack, give it Charge. ");
	}
	
	@Override
	public Minion summon() {
		Minion warsongCommander = createMinion();
		//TODO: add aura
		return warsongCommander;
	}
	

}
