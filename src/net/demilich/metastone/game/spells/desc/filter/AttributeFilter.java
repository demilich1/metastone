package net.demilich.metastone.game.spells.desc.filter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.SpellUtils;

public class AttributeFilter extends EntityFilter {

	public AttributeFilter(FilterDesc desc) {
		super(desc);
	}

	@Override
	protected boolean test(Entity entity, FilterDesc desc) {
		int targetValue = desc.getInt(FilterArg.VALUE);
		GameTag attribute = (GameTag) desc.get(FilterArg.ATTRIBUTE);
		Operation operation = (Operation) desc.get(FilterArg.OPERATION);
		if (operation == Operation.HAS) {
			return entity.hasStatus(attribute);
		}
		
		int actualValue = -1;
		if (attribute == GameTag.ATTACK) {
			actualValue = ((Actor)entity).getAttack();
		} else {
			actualValue = entity.getTagValue(attribute);
		}
		
		return SpellUtils.evaluateOperation(operation, actualValue, targetValue);
	}

}
