package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetRequirement;
import net.pferdimanzug.hearthstone.analyzer.game.actions.battlecry.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.minions.Minion;

public class CruelTaskmaster extends MinionCard {

	public CruelTaskmaster() {
		super("Cruel Taskmaster", Rarity.COMMON, HeroClass.WARRIOR, 2);
	}

	@Override
	public Minion summon() {
		Minion cruelTaskmaster = createMinion(2, 2);
		cruelTaskmaster.setTag(GameTag.BATTLECRY, new BattlecryCruelTaskmaster());
		return cruelTaskmaster;
	}
	
	private class BattlecryCruelTaskmaster extends Battlecry {
		
		public BattlecryCruelTaskmaster() {
			setTargetRequirement(TargetRequirement.ANY);
		}

		@Override
		public void execute(GameContext context, Player player) {
			getTarget().modifyTag(GameTag.ATTACK_BONUS, +2);
			context.getLogic().damage(getTarget(), 1);
		}

		@Override
		public boolean canBeExecutedOn(Entity entity) {
			return entity.getEntityType() == EntityType.MINION;
		}
		
		
		
	}

}
