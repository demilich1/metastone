package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class BuffHeroAttackSpell implements ISpell {
	private final int attackBonus;

	public BuffHeroAttackSpell(int attackBonus) {
		this.attackBonus = attackBonus;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		if (attackBonus != 0) {
			player.getHero().modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
	}
}
