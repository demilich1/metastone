package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
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

}
