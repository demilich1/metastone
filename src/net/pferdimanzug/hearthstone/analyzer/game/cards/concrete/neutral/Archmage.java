package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class Archmage extends MinionCard {
	
	public Archmage() {
		super("Archmage", Rarity.FREE, HeroClass.ANY, 6);
	}

	@Override
	public Minion summon() {
		Minion archmage = createMinion(4, 7);
		archmage.setTag(GameTag.SPELL_POWER, 1);
		return archmage;
	}

}
