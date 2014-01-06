package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;

public class BuffWeaponSpell implements ISpell {
	
	private final int damageBonus;
	private final int durabilityBonus;

	public BuffWeaponSpell(int damageBonus, int durabilityBonus) {
		this.damageBonus = damageBonus;
		this.durabilityBonus = durabilityBonus;
	}
	
	public BuffWeaponSpell(int attackBonus) {
		this(attackBonus, 0);
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		Weapon weapon = player.getHero().getWeapon();
		if (damageBonus != 0) {
			weapon.modifyTag(GameTag.WEAPON_DAMAGE, damageBonus);
		}
		if (durabilityBonus != 0) {
			weapon.modifyTag(GameTag.DURABILITY, durabilityBonus);
		}
	}

}
