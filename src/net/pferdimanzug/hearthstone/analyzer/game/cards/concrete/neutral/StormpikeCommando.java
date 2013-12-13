package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.BattlecrySingleTargetDamage;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class StormpikeCommando extends MinionCard {
	
	private static final int BATTLECRY_DAMAGE = 2;

	public StormpikeCommando() {
		super("Stormpike Commando", Rarity.FREE, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		Minion stormpikeCommando = createMinion(4, 2);
		stormpikeCommando.setTag(GameTag.BATTLECRY, new BattlecrySingleTargetDamage(BATTLECRY_DAMAGE));
		return stormpikeCommando;
	}

}
