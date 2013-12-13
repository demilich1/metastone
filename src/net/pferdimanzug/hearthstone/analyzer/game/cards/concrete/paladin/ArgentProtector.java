package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

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

public class ArgentProtector extends MinionCard {

	public ArgentProtector() {
		super("Argent Protector", Rarity.COMMON, HeroClass.PALADIN, 2);
	}

	@Override
	public Minion summon() {
		Minion argentProtector = createMinion(2, 2);
		argentProtector.setTag(GameTag.BATTLECRY, new BattlecryDivineShield());
		return argentProtector;
	}

	private class BattlecryDivineShield extends Battlecry {
		public BattlecryDivineShield() {
			setTargetRequirement(TargetRequirement.OWN_MINIONS);
			setEffectHint(EffectHint.POSITIVE);
		}

		@Override
		public void execute(GameContext context, Player player) {
			getTarget().setTag(GameTag.DIVINE_SHIELD);
		}

	}

}
