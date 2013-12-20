package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.EffectHint;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ShatteredSunCleric extends MinionCard{

	public ShatteredSunCleric() {
		super("Shattered Sun Cleric", Rarity.FREE, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion shatteredSunCleric = createMinion(3, 3);
		shatteredSunCleric.setTag(GameTag.BATTLECRY, new BuffFriendlyMinion());
		return shatteredSunCleric;
	}
	
	private class BuffFriendlyMinion extends GameAction {
		
		public BuffFriendlyMinion() {
			setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
			setEffectHint(EffectHint.POSITIVE);
			setActionType(ActionType.MINION_ABILITY);
		}

		@Override
		public void execute(GameContext context, Player player) {
			Entity target = getTarget();
			target.modifyTag(GameTag.ATTACK_BONUS, 1);
			target.modifyTag(GameTag.HP_BONUS, 1);
		}
		
	}

}
