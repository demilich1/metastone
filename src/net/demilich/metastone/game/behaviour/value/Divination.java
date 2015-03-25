package net.demilich.metastone.game.behaviour.value;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.minions.Minion;

public class Divination {
	
	public static boolean couldKillWithWeapon(GameContext context, Minion minion, int weaponDamage) {
		if (!willMinionDie(context, minion, weaponDamage)) {
			return false;
		}
		
		if (minion.hasStatus(GameTag.STEALTH)) {
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

}
