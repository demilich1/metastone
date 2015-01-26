package net.demilich.metastone.game.behaviour.value;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.PhysicalAttackAction;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.EntityType;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.entities.minions.Minion;

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
