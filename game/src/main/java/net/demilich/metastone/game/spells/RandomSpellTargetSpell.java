package net.demilich.metastone.game.spells;

import java.util.List;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.PlaySpellCardAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class RandomSpellTargetSpell extends Spell {

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (!((Card) target).getCardType().isCardType(CardType.SPELL)) {
			// In case Yogg-Saron tries to do something silly. Which he will.
			return;
		}
		SpellCard spellCard = (SpellCard) target;
		PlaySpellCardAction action = new PlaySpellCardAction(spellCard.getSpell(), spellCard, spellCard.getTargetRequirement());
		
		List<Entity> targets = context.getLogic().getValidTargets(context.getActivePlayerId(), action);
		Entity randomTarget = targets.get(context.getLogic().random(targets.size()));
		context.getEnvironment().put(Environment.TARGET_OVERRIDE, randomTarget.getReference());
	}
}
