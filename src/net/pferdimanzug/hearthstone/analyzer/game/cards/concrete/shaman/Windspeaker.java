package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class Windspeaker extends MinionCard {

	public Windspeaker() {
		super("Windspeaker", Rarity.FREE, HeroClass.SHAMAN, 4);
	}

	@Override
	public Minion summon() {
		Minion windspeaker = createMinion(3, 3);
		windspeaker.setTag(GameTag.BATTLECRY, new BattlecryWindfury());
		return windspeaker;
	}

	private class BattlecryWindfury extends Battlecry {
		public BattlecryWindfury() {
			setEffectHint(EffectHint.POSITIVE);
			setTargetRequirement(TargetRequirement.OWN_MINIONS);
		}

		@Override
		public void execute(GameContext context, Player player) {
			getTarget().setTag(GameTag.WINDFURY);
		}

	}

}
