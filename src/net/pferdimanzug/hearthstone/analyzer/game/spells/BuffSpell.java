package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class BuffSpell implements ISpell {
	
	private final int attackBonus;
	private final int hpBonus;
	private final GameTag[] tags;

	public BuffSpell(int attackBonus, int hpBonus, GameTag... tags) {
		this.attackBonus = attackBonus;
		this.hpBonus = hpBonus;
		this.tags = tags;
	}
	
	public BuffSpell(GameTag... tags) {
		this(0, 0, tags);
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		target.modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		target.modifyTag(GameTag.HP_BONUS, +hpBonus);
		for (GameTag tag : tags) {
			target.setTag(tag);
		}
	}	

}
