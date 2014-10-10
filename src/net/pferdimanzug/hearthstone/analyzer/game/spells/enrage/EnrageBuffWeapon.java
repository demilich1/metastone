package net.pferdimanzug.hearthstone.analyzer.game.spells.enrage;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class EnrageBuffWeapon extends Spell {
	
	private final int damageBonus;

	public EnrageBuffWeapon(int damageBonus) {
		this.damageBonus = damageBonus;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int weaponDamageModifier = target.hasStatus(GameTag.ENRAGED) ? +damageBonus : -damageBonus;
		Weapon weapon = player.getHero().getWeapon();
		if (weapon == null) {
			return;
		}
		weapon.modifyTag(GameTag.WEAPON_DAMAGE, weaponDamageModifier);
	}

}
