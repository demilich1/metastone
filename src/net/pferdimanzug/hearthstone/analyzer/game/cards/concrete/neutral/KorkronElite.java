package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class KorkronElite extends MinionCard {

	public KorkronElite() {
		super("Kor'kron Elite", Rarity.FREE, HeroClass.WARRIOR, 4);
	}

	@Override
	public Minion summon() {
		return createMinion(4, 3, GameTag.CHARGE);
	}

}
