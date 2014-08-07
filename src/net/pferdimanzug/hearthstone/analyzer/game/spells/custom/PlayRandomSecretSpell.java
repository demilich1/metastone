package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.Secret;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;

public class PlayRandomSecretSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(PlayRandomSecretSpell.class);
		return desc;
	}
	
	private CardCollection findSecretCards(CardCollection cardCollection) {
		CardCollection secretCards = new CardCollection();
		for (Card card : cardCollection) {
			if (card.hasTag(GameTag.SECRET)) {
				secretCards.add(card);
			}
		}
		return secretCards;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		CardCollection secretCards = findSecretCards(player.getDeck());
		
		if (secretCards.isEmpty()) {
			return;
		}
		
		SecretCard secretCard = (SecretCard) secretCards.getRandom();
		SpellDesc secretSpellDesc = secretCard.getSpell();
		Secret secret = (Secret) secretSpellDesc.get(SpellArg.SECRET);
		context.getLogic().playSecret(player,secret);
		secretCard.setLocation(CardLocation.VOID);
		player.getDeck().remove(secretCard);
	}
	
}