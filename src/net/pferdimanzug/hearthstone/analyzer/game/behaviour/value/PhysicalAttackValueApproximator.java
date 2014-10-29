package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityType;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class PhysicalAttackValueApproximator implements IValueApproximator {

	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		PhysicalAttackAction attackAction = (PhysicalAttackAction) action;
		Actor attacker = (Actor) context.resolveSingleTarget(attackAction.getAttackerReference());
		Actor defender = (Actor) context.resolveSingleTarget(attackAction.getTargetKey());
		float value = 0;
		if (defender.getEntityType() == EntityType.HERO) {
			return Values.getHeroDamageValue(context, (Hero) defender, attacker.getAttack());
		}
		if (attacker.getEntityType() == EntityType.HERO) {
			value -= Values.getHeroDamageValue(context, (Hero) attacker, defender.getAttack());
		} else {
			Minion attackerMinion = (Minion) attacker;
			if (Divination.willMinionDie(context, attackerMinion, defender.getAttack())) {
				value -= Values.getMinionValue(attackerMinion) * 3;
			} 
		}
		Minion defenderMinion = (Minion) defender;
		if (Divination.willMinionDie(context, defenderMinion, attacker.getAttack())) {
			value += Values.getMinionValue(defenderMinion) * 3;
		} 
		return value;
	}

}
