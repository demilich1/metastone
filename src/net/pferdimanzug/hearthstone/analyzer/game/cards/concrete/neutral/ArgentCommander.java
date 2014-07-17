package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ArgentCommander extends MinionCard {

	public ArgentCommander() {
		super("Argent Commander", 4, 2, Rarity.RARE, HeroClass.ANY, 6);
		setDescription("Charge, Divine Shield");
	}

	@Override
	public int getTypeId() {
		return 89;
	}



	@Override
	public Minion summon() {
		Minion argentCommander = createMinion(GameTag.CHARGE, GameTag.DIVINE_SHIELD);
		return argentCommander;
	}
}
