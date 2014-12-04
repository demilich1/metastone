package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		logger.debug("{} gains ({})", weapon, damageBonus + "/" + durabilityBonus);
		if (damageBonus != 0) {
			weapon.modifyTag(GameTag.WEAPON_DAMAGE, damageBonus);
		}
		if (durabilityBonus != 0) {
			context.getLogic().modifyDurability(weapon, durabilityBonus);
		}
	}

}
