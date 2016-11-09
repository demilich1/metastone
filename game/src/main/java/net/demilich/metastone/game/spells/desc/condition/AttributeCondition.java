package net.demilich.metastone.game.spells.desc.condition;

import java.util.List;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.filter.Operation;
import net.demilich.metastone.game.targeting.EntityReference;

public class AttributeCondition extends Condition {

	public AttributeCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity source, Entity target) {
		EntityReference entityReference = (EntityReference) desc.get(ConditionArg.TARGET);
		Entity entity = null;
		if (entityReference == null) {
			entity = target;
		} else {
			List<Entity> entities = context.resolveTarget(player, source, entityReference);
			if (entities == null || entities.isEmpty()) {
				return false;
			}
			entity = entities.get(0);
		}

		Attribute attribute = (Attribute) desc.get(ConditionArg.ATTRIBUTE);
		Operation operation = (Operation) desc.get(ConditionArg.OPERATION);
		if (operation == null || operation == Operation.HAS) {
			return entity.hasAttribute(attribute);
		}

		int targetValue = desc.getInt(ConditionArg.VALUE);

		int actualValue;
		if (attribute == Attribute.ATTACK) {
			if (entity instanceof Actor) {
				actualValue = ((Actor)entity).getAttack();	
			} else {
				actualValue = entity.getAttributeValue(attribute);
			}
			
		} else {
			actualValue = entity.getAttributeValue(attribute);
		}

		return SpellUtils.evaluateOperation(operation, actualValue, targetValue);
	}

}
