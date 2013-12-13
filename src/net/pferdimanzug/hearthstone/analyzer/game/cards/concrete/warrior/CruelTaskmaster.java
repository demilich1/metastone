package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;

public class CruelTaskmaster extends MinionCard {

	public CruelTaskmaster() {
		super("Cruel Taskmaster", Rarity.COMMON, HeroClass.WARRIOR, 2);
	}

	@Override
	public Minion summon() {
		Minion cruelTaskmaster = createMinion(2, 2);
		Battlecry battlecry = Battlecry.createBattlecry(new CruelTaskmasterSpell(), TargetRequirement.MINIONS);
		cruelTaskmaster.setTag(GameTag.BATTLECRY, battlecry);
		return cruelTaskmaster;
	}
	
	private class CruelTaskmasterSpell extends BuffSpell {
		
		public CruelTaskmasterSpell() {
			super(2, 0);
		}

		@Override
		public void cast(GameContext context, Player player, Entity target) {
			super.cast(context, player, target);
			context.getLogic().damage(target, 1);
		}
		
	}

}
