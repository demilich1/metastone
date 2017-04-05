package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.types.Secret;

public class PutRandomSecretIntoPlaySpell extends Spell {

	private CardCollection findSecretCards(CardCollection cardCollection) {
		CardCollection secretCards = new CardCollection();
		for (Card card : cardCollection) {
			if (card.hasAttribute(Attribute.SECRET)) {
				secretCards.add(card);
			}
		}
		return secretCards;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int howMany = desc.getValue(SpellArg.HOW_MANY, context, player, target, source, 1);
		for (int i = 0; i < howMany; i++) {
			CardCollection secretCards = findSecretCards(player.getDeck());

			if (secretCards.isEmpty()) {
				return;
			}
			
			secretCards.shuffle();

			SecretCard secretCard = (SecretCard) secretCards.removeFirst();
			while(!secretCards.isEmpty()) {
				if (!context.getLogic().canPlaySecret(player, secretCard)) {
					secretCard = (SecretCard) secretCards.removeFirst();	
				} else {
					break;
				}
			}
			if (secretCards.isEmpty() && !context.getLogic().canPlaySecret(player, secretCard)) {
				return;
			}
			SpellDesc secretSpellDesc = secretCard.getSpell();
			Secret secret = (Secret) secretSpellDesc.get(SpellArg.SECRET);
			context.getLogic().playSecret(player, secret, false);
			context.getLogic().removeCardFromDeck(player.getId(), secretCard);

		}
	}

}