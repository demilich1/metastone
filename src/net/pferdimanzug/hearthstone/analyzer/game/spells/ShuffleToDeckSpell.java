package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
