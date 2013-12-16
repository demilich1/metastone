package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class DalaranMage extends MinionCard {

	public DalaranMage() {
		super("Dalaran Mage", Rarity.FREE, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion dalaranMage = createMinion(1, 4);
		dalaranMage.setTag(GameTag.SPELL_POWER, 1);
		return dalaranMage;
	}

}
