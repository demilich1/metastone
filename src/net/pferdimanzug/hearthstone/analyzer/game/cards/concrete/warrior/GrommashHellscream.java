package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.enrage.Enrage;

public class GrommashHellscream extends MinionCard {

	public GrommashHellscream() {
		super("Grommash Hellscream", Rarity.LEGENDARY, HeroClass.WARRIOR, 8);
	}

	@Override
	public Minion summon() {
		Minion grommashHellscream = createMinion(4, 9, GameTag.CHARGE);
		grommashHellscream.setTag(GameTag.ENRAGE_SPELL, new Enrage(6));
		return grommashHellscream;
	}

}
