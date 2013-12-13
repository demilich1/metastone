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

public class DarkIronDwarf extends MinionCard {

	public DarkIronDwarf() {
		super("Dark Iron Dwarf", Rarity.COMMON, HeroClass.ANY, 4);
	}

	@Override
	public Minion summon() {
		Minion darkIronDwarf = createMinion(4, 4);
		darkIronDwarf.setTag(GameTag.BATTLECRY, new BattlecryDarkIronDwarf());
		return darkIronDwarf;
	}

	private class BattlecryDarkIronDwarf extends Battlecry {

		public BattlecryDarkIronDwarf() {
			setEffectHint(EffectHint.POSITIVE);
		}

		@Override
		public void execute(GameContext context, Player player) {
			getTarget().modifyTag(GameTag.ATTACK_BONUS, +2);
		}

	}

}
