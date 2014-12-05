package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class ForceTankMax extends MinionCard {

	public ForceTankMax() {
		super("Force-Tank MAX", 7, 7, Rarity.COMMON, HeroClass.ANY, 8);
		setDescription("Divine Shield");
		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion forceTankMax = createMinion(GameTag.DIVINE_SHIELD);
		return forceTankMax;
	}

}
