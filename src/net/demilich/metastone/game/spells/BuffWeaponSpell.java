package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffWeaponSpell extends Spell {

	public static SpellDesc create(int attackBonus) {
		return create(attackBonus, 0);
	}
	
	public static SpellDesc create(int attackBonus, int durabilityBonus) {
		SpellDesc desc = new SpellDesc(BuffWeaponSpell.class);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		desc.set(SpellArg.HP_BONUS, durabilityBonus);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}
	
	private static Logger logger = LoggerFactory.getLogger(BuffWeaponSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int damageBonus = desc.getInt(SpellArg.ATTACK_BONUS);
		int durabilityBonus = desc.getInt(SpellArg.HP_BONUS);
		
		Weapon weapon = player.getHero().getWeapon();
		if (weapon == null) {
			return;
		}
		
		logger.debug("{} gains ({})", weapon, damageBonus + "/" + durabilityBonus);
		if (damageBonus != 0) {
			weapon.modifyTag(GameTag.WEAPON_DAMAGE, damageBonus);
		}
		if (durabilityBonus != 0) {
			context.getLogic().modifyDurability(weapon, durabilityBonus);
		}
	}

}
