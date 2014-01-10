package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class FrostwolfWarlord extends MinionCard {

	public FrostwolfWarlord() {
		super("Frostwolf Warlord", Rarity.FREE, HeroClass.ANY, 5);
	}

	@Override
	public Minion summon() {
		Minion frostwolfWarlord = createMinion(4, 4);
		Battlecry battlecry = Battlecry.createBattlecry(new BuffLeadership(), TargetSelection.SELF);
		frostwolfWarlord.setTag(GameTag.BATTLECRY, battlecry);
		return frostwolfWarlord;
	}

	private class BuffLeadership extends BuffSpell {
		
		public BuffLeadership() {
			super(0, 0);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			int minionCount = player.getMinions().size();
			setAttackBonus(minionCount);
			setHpBonus(minionCount);
			super.cast(context, player, target);
		}

	}

}
