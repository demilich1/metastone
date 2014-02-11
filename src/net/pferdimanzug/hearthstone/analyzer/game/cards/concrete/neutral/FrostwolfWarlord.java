package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FrostwolfWarlord extends MinionCard {

	private class BuffLeadership extends BuffSpell {
		
		public BuffLeadership() {
			super(0, 0);
		}

		@Override
		protected void onCast(GameContext context, Player player, Actor target) {
			int minionCount = player.getMinions().size();
			setAttackBonus(minionCount);
			setHpBonus(minionCount);
			super.onCast(context, player, target);
		}

	}

	public FrostwolfWarlord() {
		super("Frostwolf Warlord", 4, 4, Rarity.FREE, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		Minion frostwolfWarlord = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new BuffLeadership(), TargetSelection.SELF);
		frostwolfWarlord.setTag(GameTag.BATTLECRY, battlecry);
		return frostwolfWarlord;
	}

}
