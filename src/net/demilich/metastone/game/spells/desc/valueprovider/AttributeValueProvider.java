package net.demilich.metastone.game.spells.desc.valueprovider;

import java.util.List;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.targeting.EntityReference;

public class AttributeValueProvider extends ValueProvider {
	
	public AttributeValueProvider(ValueProviderDesc desc) {
		super(desc);
	}

	@Override
	protected int provideValue(GameContext context, Player player, Entity target) {
		EntityReference sourceReference = (EntityReference) desc.get(ValueProviderArg.SOURCE);
		GameTag attribute = (GameTag) desc.get(ValueProviderArg.ATTRIBUTE);
		List<Entity> entities = context.resolveTarget(player, (Actor)target, sourceReference);
		if (entities == null) {
			return 0;
		}
		int value = 0;
		for (Entity entity : entities) {
			Actor source = (Actor) entity;	
			if (attribute == GameTag.ATTACK) {
				value += source.getAttack();
			} else {
				value += source.getTagValue(attribute);
			}
		}

		return value;
	}

}
