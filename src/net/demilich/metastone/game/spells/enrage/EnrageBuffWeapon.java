package net.demilich.metastone.game.spells.enrage;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
