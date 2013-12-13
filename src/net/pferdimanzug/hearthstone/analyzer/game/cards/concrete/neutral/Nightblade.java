package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class Nightblade extends MinionCard {
	
	public static final int BATTLECRY_DAMAGE = 3;

	public Nightblade() {
		super("Nightblade", Rarity.FREE, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		Minion nightblade = createMinion(4, 4);
		nightblade.setTag(GameTag.BATTLECRY, new BattlecryNightblade());
		return nightblade;
	}
	
	private class BattlecryNightblade extends Battlecry {
		
		public BattlecryNightblade() {
			setEffectHint(EffectHint.NEGATIVE);
		}

		@Override
		public void execute(GameContext context, Player player) {
			Hero enemyHero = context.getOpponent(player).getHero();
			context.getLogic().damage(enemyHero, BATTLECRY_DAMAGE);
		}
		
	}

}
