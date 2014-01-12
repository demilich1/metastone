package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffHeroSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(BuffHeroSpell.class);

	private final int attackBonus;
	private final int armorBonus;

	public BuffHeroSpell(int attackBonus, int armorBonus) {
		this.attackBonus = attackBonus;
		this.armorBonus = armorBonus;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Hero hero = player.getHero();
		if (attackBonus != 0) {
			logger.debug("{} gains {} attack", hero, attackBonus);
			hero.modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
		if (armorBonus != 0) {
			logger.debug("{} gains {} armor", hero, armorBonus);
			hero.modifyArmor(+armorBonus);
		}
	}

}
