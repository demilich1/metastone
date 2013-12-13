package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.BattlecrySingleTargetDamage;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class ElvenArcher extends MinionCard {

	private static final int BATTLECRY_DAMAGE = 1;

	public ElvenArcher() {
		super("Elven Archer", Rarity.FREE, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		Minion elvenArcher = createMinion(1, 1);
		elvenArcher.setTag(GameTag.BATTLECRY, new BattlecrySingleTargetDamage(BATTLECRY_DAMAGE));
		return elvenArcher;
	}

}
