package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class DrawCardAndDoSomethingSpell extends Spell {

	public static SpellDesc create(ICardProvider cardProvider, ICardPostProcessor cardProcessor) {
		SpellDesc desc = new SpellDesc(DrawCardAndDoSomethingSpell.class);
		desc.set(SpellArg.CARD_PROVIDER, cardProvider);
		desc.set(SpellArg.CARD_PROCESSOR, cardProcessor);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		ICardProvider cardProvider = (ICardProvider) desc.get(SpellArg.CARD_PROVIDER);
		Card card = cardProvider.getCard(context, player);
		// card may be null (i.e. try to draw from deck, but already in fatigue)
		if (card == null) {
			return;
		}
		ICardPostProcessor cardPostProcessor = (ICardPostProcessor) desc.get(SpellArg.CARD_PROCESSOR);
		cardPostProcessor.processCard(context, player, card);
	}

}
