package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class KorkronElite extends MinionCard {

	public KorkronElite() {
		super("Kor'kron Elite", 4, 3, Rarity.FREE, HeroClass.WARRIOR, 4);
		setDescription("Charge");
	}

	@Override
	public Minion summon() {
		return createMinion(GameTag.CHARGE);
	}

}
