package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class BaronRivendare extends MinionCard {

	public BaronRivendare() {
		super("Baron Rivendare", 1, 7, Rarity.LEGENDARY, HeroClass.ANY, 4);
		setDescription("Your minions trigger their Deathrattles twice.");
	}

	@Override
	public int getTypeId() {
		return 413;
	}

	@Override
	public Minion summon() {
		Minion baronRivendare = createMinion(GameTag.DOUBLE_DEATHRATTLES);
		return baronRivendare;
	}
}
