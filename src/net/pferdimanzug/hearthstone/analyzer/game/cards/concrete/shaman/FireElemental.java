package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.BattlecrySingleTargetDamage;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class FireElemental extends MinionCard {
	
	private static final int BATTLECRY_DAMAGE = 3;
	
	public FireElemental() {
		super("Fire Elemental", Rarity.FREE, HeroClass.SHAMAN, 6);
	}

	@Override
	public Minion summon() {
		Minion fireElemental = createMinion(6, 5);
		fireElemental.setTag(GameTag.BATTLECRY, new BattlecrySingleTargetDamage(BATTLECRY_DAMAGE));
		return fireElemental;
	}

}
