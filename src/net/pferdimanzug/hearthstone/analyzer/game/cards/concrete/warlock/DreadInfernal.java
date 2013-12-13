package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Race;

public class DreadInfernal extends MinionCard {

	public DreadInfernal() {
		super("Dread Infernal", Rarity.FREE, HeroClass.WARLOCK, 6);
	}

	@Override
	public Minion summon() {
		Minion dreadInfernal = createMinion(6, 6, Race.DEMON);
		dreadInfernal.setTag(GameTag.BATTLECRY, new Inferno());
		return dreadInfernal;
	}
	
	private class Inferno extends GameAction {
		
		private static final int DAMAGE = 1;
		
		public Inferno() {
			setActionType(ActionType.MINION_ABILITY);
		}

		@Override
		public void execute(GameContext context, Player player) {
			Player opponent = context.getOpponent(player);
			context.getLogic().damage(opponent.getHero(), DAMAGE);
			for (Entity minion : opponent.getMinions()) {
				context.getLogic().damage(minion, DAMAGE);
			}
			context.getLogic().damage(player.getHero(), DAMAGE);
			for (Entity minion : player.getMinions()) {
				context.getLogic().damage(minion, DAMAGE);
			}
		}
		
	}

}
