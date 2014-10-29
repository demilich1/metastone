package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class Divination {
	
	public static boolean willMinionDie(GameContext context, Minion minion, int damage) 
	{
		if (minion.hasStatus(GameTag.CANNOT_REDUCE_HP_BELOW_1)) {
			return false;
		}
		if (minion.hasStatus(GameTag.IMMUNE)) {
			return false;
		}
		if (minion.hasStatus(GameTag.DIVINE_SHIELD)) {
			return false;
		}
		return minion.getHp() <= damage;
	}
	
	public static boolean couldKillWithWeapon(GameContext context, Minion minion, int weaponDamage) {
		if (!willMinionDie(context, minion, weaponDamage)) {
			return false;
		}
		
		if (minion.hasStatus(GameTag.STEALTHED)) {
			return false;
		}
		
		
		Player minionOwner = context.getPlayer(minion.getOwner());
		if (minion.getAttack() >= context.getOpponent(minionOwner).getHero().getEffectiveHp()) {
			return false;
		}
		for (Minion otherMinion : minionOwner.getMinions()) {
			if (otherMinion == minion) {
				continue;
			}
			if (otherMinion.hasStatus(GameTag.TAUNT)) {
				return false;
			}
		}
		
		return true;
	}

}
