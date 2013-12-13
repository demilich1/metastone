package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class BuffHeroSpell implements ISpell {

	private final int attackBonus;
	private final int armorBonus;

	public BuffHeroSpell(int attackBonus, int armorBonus) {
		this.attackBonus = attackBonus;
		this.armorBonus = armorBonus;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		if (attackBonus != 0) {
			player.getHero().modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
		if (armorBonus != 0) {
			player.getHero().modifyArmor(+armorBonus);
		}

	}

}
