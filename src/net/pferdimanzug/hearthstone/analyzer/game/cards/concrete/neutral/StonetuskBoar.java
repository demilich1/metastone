package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

public class StonetuskBoar extends MinionCard {

	public StonetuskBoar() {
		super("Stonetusk Boar", Rarity.FREE, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		return createMinion(1, 1, Race.BEAST, GameTag.CHARGE);
	}

}
