package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffHeroSpell extends RevertableSpell {

	public static SpellDesc create(int attackBonus, int armorBonus) {
		return create(attackBonus, armorBonus, true);
	}

	public static SpellDesc create(int attackBonus, int armorBonus, boolean revert) {
		SpellDesc desc = new SpellDesc(BuffHeroSpell.class);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		desc.set(SpellArg.ARMOR_BONUS, armorBonus);
		if (revert) {
			desc.set(SpellArg.REVERT_TRIGGER, new TurnEndTrigger());
		}
		desc.setTarget(EntityReference.FRIENDLY_HERO);
		return desc;
	}
	
	private static Logger logger = LoggerFactory.getLogger(BuffHeroSpell.class);

	@Override
	protected SpellDesc getReverseSpell(SpellDesc desc) {
		return create(-desc.getInt(SpellArg.ATTACK_BONUS), 0, false);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Hero hero = (Hero) target;
		int attackBonus = desc.getInt(SpellArg.ATTACK_BONUS);
		int armorBonus = desc.getInt(SpellArg.ARMOR_BONUS);
		
		if (attackBonus != 0) {
			logger.debug("{} gains {} attack", hero, attackBonus);
			hero.modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
		if (armorBonus != 0) {
			context.getLogic().gainArmor(player, armorBonus);
		}

		if (attackBonus != 0) {
			super.onCast(context, player, desc, target);
		}
	}

}
