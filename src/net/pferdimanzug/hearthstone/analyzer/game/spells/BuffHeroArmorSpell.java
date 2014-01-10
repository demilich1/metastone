package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class BuffHeroArmorSpell implements ISpell {

	private final int armorBonus;

	public BuffHeroArmorSpell(int armorBonus) {
		this.armorBonus = armorBonus;
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		if (armorBonus != 0) {
			player.getHero().modifyArmor(+armorBonus);
		}

	}

}
