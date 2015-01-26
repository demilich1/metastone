package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.Hero;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffHeroSpell extends Spell {

	public static SpellDesc create(int attackBonus, int armorBonus) {
		SpellDesc desc = new SpellDesc(BuffHeroSpell.class);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		desc.set(SpellArg.ARMOR_BONUS, armorBonus);
		desc.setTarget(EntityReference.FRIENDLY_HERO);
		return desc;
	}

	private static Logger logger = LoggerFactory.getLogger(BuffHeroSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Hero hero = (Hero) target;
		int attackBonus = desc.getInt(SpellArg.ATTACK_BONUS);
		int armorBonus = desc.getInt(SpellArg.ARMOR_BONUS);

		if (attackBonus != 0) {
			logger.debug("{} gains {} attack", hero, attackBonus);
			hero.modifyTag(GameTag.TEMPORARY_ATTACK_BONUS, +attackBonus);
		}
		if (armorBonus != 0) {
			context.getLogic().gainArmor(player, armorBonus);
		}
	}

}
