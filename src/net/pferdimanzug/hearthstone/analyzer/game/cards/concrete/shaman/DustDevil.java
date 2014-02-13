package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class DustDevil extends MinionCard {

	public DustDevil() {
		super("Dust Devil", 3, 1, Rarity.COMMON, HeroClass.SHAMAN, 1);
		setDescription("Windfury. Overload: (2)");
		setTag(GameTag.OVERLOAD, 2);
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.WINDFURY);
	}

}
