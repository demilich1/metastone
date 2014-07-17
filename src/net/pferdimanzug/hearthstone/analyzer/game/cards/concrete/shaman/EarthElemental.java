package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class EarthElemental extends MinionCard {

	public EarthElemental() {
		super("Earth Elemental", 7, 8, Rarity.EPIC, HeroClass.SHAMAN, 5);
		setDescription("Taunt. Overload: (3)");
		setTag(GameTag.OVERLOAD, 3);
	}

	@Override
	public int getTypeId() {
		return 315;
	}



	@Override
	public Minion summon() {
		return createMinion(GameTag.TAUNT);
	}
}
