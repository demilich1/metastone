package net.pferdimanzug.hearthstone.analyzer.game.spells.enrage;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class EnrageBuffWeapon extends Spell {

	public static SpellDesc create(int attackBonus) {
		SpellDesc desc = new SpellDesc(EnrageBuffWeapon.class);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int damageBonus = desc.getInt(SpellArg.ATTACK_BONUS);
		int weaponDamageModifier = target.hasStatus(GameTag.ENRAGED) ? +damageBonus : -damageBonus;
		Weapon weapon = player.getHero().getWeapon();
		if (weapon == null) {
			return;
		}
		weapon.modifyTag(GameTag.WEAPON_DAMAGE, weaponDamageModifier);
	}

}
