package net.pferdimanzug.hearthstone.analyzer.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class BuffSpell implements ISpell {
	
	private static Logger logger = LoggerFactory.getLogger(BuffSpell.class);
	
	private int attackBonus;
	private int hpBonus;

	public BuffSpell(int attackBonus, int hpBonus) {
		this.attackBonus = attackBonus;
		this.hpBonus = hpBonus;
	}
	
	@Override
	public void cast(GameContext context, Player player, Entity target) {
		logger.debug("{} gains ({})", target.getName(), attackBonus + "/" + hpBonus);
		if (attackBonus != 0) {
			target.modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
		if (hpBonus != 0) {
			target.modifyHpBonus(+hpBonus);
		}
	}

	public int getAttackBonus() {
		return attackBonus;
	}

	public void setAttackBonus(int attackBonus) {
		this.attackBonus = attackBonus;
	}

	public int getHpBonus() {
		return hpBonus;
	}

	public void setHpBonus(int hpBonus) {
		this.hpBonus = hpBonus;
	}	

}
