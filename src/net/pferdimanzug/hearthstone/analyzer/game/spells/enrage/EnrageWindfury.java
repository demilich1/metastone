package net.pferdimanzug.hearthstone.analyzer.game.spells.enrage;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class EnrageWindfury extends Enrage {

	public EnrageWindfury(int attackBonus) {
		super(attackBonus);
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		super.cast(context, player, target);
		if (target.hasTag(GameTag.ENRAGED)) {
			target.setTag(GameTag.WINDFURY);
		} else {
			target.removeTag(GameTag.WINDFURY);
		}
	}
	
	

}
