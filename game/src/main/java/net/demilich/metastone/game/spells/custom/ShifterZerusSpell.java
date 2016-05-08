package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;
import net.demilich.metastone.game.spells.desc.trigger.TriggerDesc;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;

public class ShifterZerusSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Card card = (Card) target;

		EntityFilter cardFilter = (EntityFilter) desc.get(SpellArg.CARD_FILTER);
		Card newCard = SpellUtils.getRandomCard(CardCatalogue.query(context.getDeckFormat()), filterCard -> cardFilter.matches(context, player, filterCard));
		context.getLogic().replaceCard(player.getId(), card, newCard);
		
		Map<EventTriggerArg, Object> arguments = EventTriggerDesc.build(TurnStartTrigger.class);
		arguments.put(EventTriggerArg.TARGET_PLAYER, TargetPlayer.SELF);
		EventTriggerDesc eventTriggerDesc = new EventTriggerDesc(arguments);
		
		TriggerDesc triggerDesc = new TriggerDesc();
		triggerDesc.eventTrigger = eventTriggerDesc;
		triggerDesc.spell = desc;
		
		context.getLogic().addGameEventListener(player, triggerDesc.create(), newCard);
	}

}
