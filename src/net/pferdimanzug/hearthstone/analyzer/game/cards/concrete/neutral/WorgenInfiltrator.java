package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class WorgenInfiltrator extends MinionCard {

	public WorgenInfiltrator() {
		super("Worgen Infiltrator", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Stealth");
	}

	@Override
	public int getTypeId() {
		return 229;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.STEALTHED);
	}
}
