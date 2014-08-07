package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffSpell extends RevertableSpell {

	public static SpellDesc create(int attackBonus) {
		return create(attackBonus, 0);
	}

	public static SpellDesc create(int attackBonus, int hpBonus) {
		return create(attackBonus, hpBonus, null);
	}

	public static SpellDesc create(int attackBonus, int hpBonus, boolean temporary) {
		return create(attackBonus, hpBonus, temporary ? new TurnEndTrigger() : null);
	}

	public static SpellDesc create(int attackBonus, int hpBonus, GameEventTrigger revertTrigger) {
		SpellDesc desc = new SpellDesc(BuffSpell.class);
		desc.set(SpellArg.ATTACK_BONUS, attackBonus);
		desc.set(SpellArg.HP_BONUS, hpBonus);
		if (revertTrigger != null) {
			desc.set(SpellArg.REVERT_TRIGGER, revertTrigger);
		}

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
	public SpellDesc getReverseSpell(SpellDesc desc) {
		return create(-desc.getInt(SpellArg.ATTACK_BONUS), -desc.getInt(SpellArg.HP_BONUS));
	}

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

		super.onCast(context, player, desc, target);
	}

}
