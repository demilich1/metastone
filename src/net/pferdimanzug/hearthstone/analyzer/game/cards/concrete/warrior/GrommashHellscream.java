package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.enrage.Enrage;

public class GrommashHellscream extends MinionCard {

	public GrommashHellscream() {
		super("Grommash Hellscream", 4, 9, Rarity.LEGENDARY, HeroClass.WARRIOR, 8);
		setDescription("Charge. Enrage: +6 Attack ");
	}

	@Override
	public Minion summon() {
		Minion grommashHellscream = createMinion(GameTag.CHARGE);
		grommashHellscream.setTag(GameTag.ENRAGE_SPELL, new Enrage(6));
		return grommashHellscream;
	}

}
