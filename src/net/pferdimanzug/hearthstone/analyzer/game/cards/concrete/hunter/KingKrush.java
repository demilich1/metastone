package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;

public class KingKrush extends MinionCard {

	public KingKrush() {
		super("King Krush", 8, 8, Rarity.LEGENDARY, HeroClass.HUNTER, 9);
		setDescription("Charge");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 39;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}
}
