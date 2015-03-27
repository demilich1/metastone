package net.demilich.metastone.game.spells.desc.valueprovider;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.targeting.EntityReference;

public class AttributeValueProvider implements IValueProvider {
	
	private final ValueProviderDesc desc;

	public AttributeValueProvider(ValueProviderDesc desc) {
		this.desc = desc;
	}

	@Override
	public int provideValue(GameContext context, Player player, Entity target) {
		EntityReference sourceReference = (EntityReference) desc.get(ValueProviderArg.SOURCE);
		GameTag attribute = (GameTag) desc.get(ValueProviderArg.ATTRIBUTE);
		Actor source = (Actor) context.resolveTarget(player, null, sourceReference).get(0);
		if (attribute == GameTag.ATTACK) {
			return source.getAttack();
		} 

		return source.getTagValue(attribute);
	}

}
