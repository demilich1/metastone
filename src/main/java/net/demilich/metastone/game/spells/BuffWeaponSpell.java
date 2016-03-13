package net.demilich.metastone.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class BuffWeaponSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(BuffWeaponSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int damageBonus = desc.getValue(SpellArg.ATTACK_BONUS, context, player, target, source, 0);
		int durabilityBonus = desc.getValue(SpellArg.HP_BONUS, context, player, target, source, 0);

		Weapon weapon = (Weapon) target;
		if (weapon == null) {
			return;
		}

		logger.debug("{} gains ({})", weapon, damageBonus + "/" + durabilityBonus);
		if (damageBonus != 0) {
			weapon.modifyAttribute(Attribute.ATTACK, damageBonus);
		}
		if (durabilityBonus != 0) {
			context.getLogic().modifyDurability(weapon, durabilityBonus);
		}
	}

}
