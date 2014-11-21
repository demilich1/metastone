package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		ICardPostProcessor cardPostProcessor = (ICardPostProcessor) desc.get(SpellArg.CARD_PROCESSOR);
		cardPostProcessor.processCard(context, player, card);
	}

}
