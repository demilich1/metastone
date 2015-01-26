package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemporaryAttackSpell extends Spell {

	public static SpellDesc create(int attackBonus) {
		SpellDesc desc = new SpellDesc(TemporaryAttackSpell.class);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		return desc;
	}

	private static Logger logger = LoggerFactory.getLogger(TemporaryAttackSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int attackBonus = desc.getInt(SpellArg.ATTACK_BONUS);

		IValueProvider attackValueProvider = (IValueProvider) desc.get(SpellArg.VALUE_PROVIDER);

		if (attackValueProvider != null) {
			attackBonus = attackValueProvider.provideValue(context, player, target);
		}

		logger.debug("{} gains {} attack", target, attackBonus);

		Actor targetActor = (Actor) target;

		if (attackBonus != 0) {
			targetActor.modifyTag(GameTag.TEMPORARY_ATTACK_BONUS, +attackBonus);
		}
	}

}
