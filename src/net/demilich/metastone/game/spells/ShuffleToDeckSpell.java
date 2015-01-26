package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ShuffleToDeckSpell extends Spell {

	public static SpellDesc create(TargetPlayer targetPlayer, Card card) {
		SpellDesc desc = new SpellDesc(ShuffleToDeckSpell.class);
		desc.set(SpellArg.CARD, card);
		desc.setTargetPlayer(targetPlayer);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Card targetCard = (Card) desc.get(SpellArg.CARD);
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			shuffleToDeck(player, targetCard);
			shuffleToDeck(opponent, targetCard);
			break;
		case OPPONENT:
			shuffleToDeck(opponent, targetCard);
			break;
		case SELF:
			shuffleToDeck(player, targetCard);
			break;
		default:
			break;
		}

	}

	private void shuffleToDeck(Player player, Card card) {
		Card randomCard = player.getDeck().getRandom();
		if (randomCard == null) {
			player.getDeck().add(card.clone());
		} else {
			player.getDeck().addAfter(card.clone(), randomCard);
		}
	}

}
