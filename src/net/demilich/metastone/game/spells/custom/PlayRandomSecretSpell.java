package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.SecretCard;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;
import net.demilich.metastone.game.targeting.EntityReference;

public class PlayRandomSecretSpell extends Spell {
	
	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(PlayRandomSecretSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}
	
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
		CardCollection secretCards = findSecretCards(player.getDeck());
		
		if (secretCards.isEmpty()) {
			return;
		}
		
		SecretCard secretCard = (SecretCard) secretCards.getRandom();
		SpellDesc secretSpellDesc = secretCard.getSpell();
		Secret secret = (Secret) secretSpellDesc.get(SpellArg.SECRET);
		context.getLogic().playSecret(player,secret);
		context.getLogic().removeCard(player.getId(), secretCard);
		player.getDeck().remove(secretCard);
	}
	
}