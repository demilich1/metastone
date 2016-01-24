package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.AlgebraicOperation;

public class ModifyDamageSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (context.getDamageStack().isEmpty()) {
			return;
		}
		
		int damage = context.getDamageStack().pop();
		AlgebraicOperation operation = (AlgebraicOperation) desc.get(SpellArg.OPERATION);
		int value = desc.getValue();
		int minDamage = desc.getInt(SpellArg.MIN_DAMAGE, 0);
		switch(operation) {
		case ADD:
			if (((Actor) context.getEnvironment().get(Environment.EVENT_TARGET)).hasAttribute(Attribute.TAKE_DOUBLE_DAMAGE)) {
				value *= 2;
			}
			damage += value;
			break;
		case SUBTRACT:
			if (((Actor) context.getEnvironment().get(Environment.EVENT_TARGET)).hasAttribute(Attribute.TAKE_DOUBLE_DAMAGE)) {
				value *= 2;
			}
			damage -= value;
			damage = Math.max(minDamage, damage);
			break;
		case MULTIPLY:
			damage *= value;
			break;
		case DIVIDE:
			damage /= value;
			damage = Math.max(minDamage, damage);
			break;
		case SET:
			if (((Actor) context.getEnvironment().get(Environment.EVENT_TARGET)).hasAttribute(Attribute.TAKE_DOUBLE_DAMAGE)) {
				value *= 2;
			}
			damage = value;
			break;
		default:
			break;
		}
		
		context.getDamageStack().push(damage);
	}

}
