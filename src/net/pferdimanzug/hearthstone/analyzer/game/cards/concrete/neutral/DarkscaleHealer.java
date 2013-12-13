package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class DarkscaleHealer extends MinionCard {

	public DarkscaleHealer(String name, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, rarity, classRestriction, manaCost);
	}

	@Override
	public Minion summon() {
		Minion darkscaleHealer = createMinion(4, 5);
		darkscaleHealer.setTag(GameTag.BATTLECRY, new DarkscaleHeal());
		return darkscaleHealer;
	}

	private class DarkscaleHeal extends GameAction {

		public static final int HEALING = 2;

		public DarkscaleHeal() {
			setActionType(ActionType.MINION_ABILITY);
			setEffectHint(EffectHint.POSITIVE);
		}

		@Override
		public void execute(GameContext context, Player player) {
			context.getLogic().heal(player.getHero(), HEALING);
			for (Entity minion : player.getMinions()) {
				context.getLogic().heal(minion, HEALING);
			}
		}

	}

}
