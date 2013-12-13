package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class NoviceEngineer extends MinionCard {

	public NoviceEngineer() {
		super("Novice Engineer", Rarity.FREE, HeroClass.ANY, 2);
	}

	@Override
	public Minion summon() {
		Minion noviceEngineer = createMinion(1, 2);
		noviceEngineer.setTag(GameTag.BATTLECRY, new BattlecryNoviceEngineer());
		return noviceEngineer;
	}
	
	private class BattlecryNoviceEngineer extends Battlecry {
		
		public BattlecryNoviceEngineer() {
			setEffectHint(EffectHint.POSITIVE);
		}

		@Override
		public void execute(GameContext context, Player player) {
			context.getLogic().drawCard(player);
		}
		
	}

}
