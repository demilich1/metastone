package net.demilich.metastone.game.spells.desc.valueprovider;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.targeting.EntityReference;

public class HighestAttributeValueProvider extends ValueProvider {

	public HighestAttributeValueProvider(ValueProviderDesc desc) {
		super(desc);
	}

	@Override
	protected int provideValue(GameContext context, Player player, Entity target, Entity host) {
		EntityReference sourceReference = (EntityReference) desc.get(ValueProviderArg.TARGET);
		Attribute attribute = (Attribute) desc.get(ValueProviderArg.ATTRIBUTE);
		List<Entity> entities = null;
		if (sourceReference != null) {
			entities = context.resolveTarget(player, host, sourceReference);
		} else {
			entities = new ArrayList<>();
			entities.add(target);
		}
		if (entities == null) {
			return 0;
		}

		EntityFilter filter = (EntityFilter) desc.get(ValueProviderArg.FILTER);		
		int value = 0;
		for (Entity entity : entities) {
			if (filter != null && !filter.matches(context, player, entity)) {
				continue;
			}
			if (entity instanceof Card) {
				Card card = (Card) entity;
				if (attribute == Attribute.ATTACK) {
					value = Math.max(card.getAttributeValue(Attribute.ATTACK) + card.getAttributeValue(Attribute.ATTACK_BONUS), value);
				} else if (attribute == Attribute.MAX_HP) {
					value = Math.max(card.getAttributeValue(Attribute.MAX_HP) + card.getAttributeValue(Attribute.HP_BONUS), value);
				} else {
					value = Math.max(card.getAttributeValue(attribute), value);
				}
			} else {
				Actor source = (Actor) entity;
				if (attribute == Attribute.ATTACK) {
					value = Math.max(source.getAttack(), value);
				} else if (attribute == Attribute.MAX_HP) {
					value = Math.max(source.getMaxHp(), value);
				} else {
					value = Math.max(source.getAttributeValue(attribute), value);
				}
			}
		}

		return value;
	}

}
