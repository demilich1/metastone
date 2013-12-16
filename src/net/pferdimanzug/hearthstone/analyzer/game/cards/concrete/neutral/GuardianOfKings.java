package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class GuardianOfKings extends MinionCard {

	public GuardianOfKings() {
		super("Guardian of Kings", Rarity.FREE, HeroClass.PALADIN, 7);
	}

	@Override
	public Minion summon() {
		Minion guardianOfKings = createMinion(5, 6);
		guardianOfKings.setTag(GameTag.BATTLECRY, new HealHero());
		return guardianOfKings;
	}
	
	private class HealHero extends GameAction {
		
		public static final int HEALING = 6;
		
		public HealHero() {
			setActionType(ActionType.MINION_ABILITY);
			setEffectHint(EffectHint.POSITIVE);
		}

		@Override
		public void execute(GameContext context, Player player) {
			context.getLogic().heal(player.getHero(), HEALING);
		}
		
	}

}
