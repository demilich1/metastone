package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class MurlocRaider extends MinionCard {

	public MurlocRaider() {
		super("Murloc Raider", Rarity.FREE, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		return createMinion(2, 1, Race.MURLOC);
	}

}
