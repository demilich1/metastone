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

public class BuffSpell extends Spell {

	public static SpellDesc create(int attackBonus) {
		return create(attackBonus, 0);
	}

	public static SpellDesc create(int attackBonus, int hpBonus) {
		SpellDesc desc = new SpellDesc(BuffSpell.class);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		desc.set(SpellArg.HP_BONUS, hpBonus);
		return desc;
	}

	public static SpellDesc create(IValueProvider attackValueProvider, IValueProvider hpValueProvider) {
		SpellDesc desc = new SpellDesc(BuffSpell.class);
		desc.set(SpellArg.VALUE_PROVIDER, attackValueProvider);
		desc.set(SpellArg.SECOND_VALUE_PROVIDER, hpValueProvider);
		return desc;
	}

	private static Logger logger = LoggerFactory.getLogger(BuffSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int attackBonus = desc.getInt(SpellArg.ATTACK_BONUS);
		int hpBonus = desc.getInt(SpellArg.HP_BONUS);

		IValueProvider attackValueProvider = (IValueProvider) desc.get(SpellArg.VALUE_PROVIDER);
		IValueProvider hpValueProvider = (IValueProvider) desc.get(SpellArg.SECOND_VALUE_PROVIDER);

		if (attackValueProvider != null) {
			attackBonus = attackValueProvider.provideValue(context, player, target);
		}
		if (hpValueProvider != null) {
			hpBonus = hpValueProvider.provideValue(context, player, target);
		}

		logger.debug("{} gains ({})", target, attackBonus + "/" + hpBonus);

		Actor targetActor = (Actor) target;

		if (attackBonus != 0) {
			targetActor.modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
		if (hpBonus != 0) {
			targetActor.modifyHpBonus(+hpBonus);
		}
	}

}
