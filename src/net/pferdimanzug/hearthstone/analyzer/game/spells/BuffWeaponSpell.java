package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffWeaponSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(BuffWeaponSpell.class);

	private final int damageBonus;
	private final int durabilityBonus;

	public BuffWeaponSpell(int attackBonus) {
		this(attackBonus, 0);
	}

	public BuffWeaponSpell(int damageBonus, int durabilityBonus) {
		this.damageBonus = damageBonus;
		this.durabilityBonus = durabilityBonus;
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		Weapon weapon = player.getHero().getWeapon();
		logger.debug("{} gains ({})", weapon, damageBonus + "/" + durabilityBonus);
		if (damageBonus != 0) {
			weapon.modifyTag(GameTag.WEAPON_DAMAGE, damageBonus);
		}
		if (durabilityBonus != 0) {
			weapon.modifyTag(GameTag.DURABILITY, durabilityBonus);
		}
	}

}
