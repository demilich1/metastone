package net.demilich.metastone.game.spells.desc.condition;

import java.util.List;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.targeting.EntityReference;

public class HasAttackedCondition extends Condition {

	public HasAttackedCondition(ConditionDesc desc) {
		super(desc);
	}

	@Override
	protected boolean isFulfilled(GameContext context, Player player, ConditionDesc desc, Entity target) {
		EntityReference sourceReference = (EntityReference) desc.get(ConditionArg.TARGET);
		Entity source = null;
		if (sourceReference == null) {
			source = target;
		} else {
			List<Entity> entities = context.resolveTarget(player, null, sourceReference);
			if (entities == null || entities.isEmpty()) {
				return false;
			}
			source = entities.get(0);
		}
		if (source != null && source instanceof Actor) {
			Actor actor = (Actor) source;
			return actor.getMaxNumberOfAttacks() > actor.getAttributeValue(Attribute.NUMBER_OF_ATTACKS);
		}
		return false;
	}

}
