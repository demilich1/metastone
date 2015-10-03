package net.demilich.metastone.game.spells;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;

public class BuffSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(BuffSpell.class);

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int attackBonus = desc.getInt(SpellArg.ATTACK_BONUS, 0);
		int hpBonus = desc.getInt(SpellArg.HP_BONUS, 0);

		ValueProvider valueProvider = (ValueProvider) desc.get(SpellArg.VALUE_PROVIDER);
		ValueProvider attackValueProvider = (ValueProvider) desc.get(SpellArg.ATTACK_VALUE_PROVIDER);
		ValueProvider hpValueProvider = (ValueProvider) desc.get(SpellArg.HP_VALUE_PROVIDER);

		if (attackValueProvider != null) {
			attackBonus = attackValueProvider.getValue(context, player, target, source);
		} else if (valueProvider != null) {
			attackBonus = valueProvider.getValue(context, player, target, source);
		}

		if (hpValueProvider != null) {
			hpBonus = hpValueProvider.getValue(context, player, target, source);
		} else if (valueProvider != null) {
			hpBonus = valueProvider.getValue(context, player, target, source);
		}

		logger.debug("{} gains ({})", target, attackBonus + "/" + hpBonus);

		if (attackBonus != 0) {
			target.modifyAttribute(Attribute.ATTACK_BONUS, +attackBonus);
		}
		if (hpBonus != 0) {
			target.modifyHpBonus(+hpBonus);
		}
	}

}
