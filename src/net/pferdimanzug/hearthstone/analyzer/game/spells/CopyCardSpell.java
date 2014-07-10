package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyCardSpell extends Spell {

	private static Logger logger = LoggerFactory.getLogger(CopyCardSpell.class);

	private final CardLocation cardLocation;
	private final int numberOfCardsToCopy;

	public CopyCardSpell(CardLocation cardLocation, int numberOfCardsToCopy) {
		this.cardLocation = cardLocation;
		this.numberOfCardsToCopy = numberOfCardsToCopy;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Player opponent = context.getOpponent(player);
		CardCollection source = null;
		switch (cardLocation) {
		case DECK:
			source = opponent.getDeck();
			break;
		case GRAVEYARD:
			source = opponent.getGraveyard();
			break;
		case HAND:
			source = opponent.getHand();
			break;
		case HERO_POWER:
		case PENDING:
		default:
			logger.error("Trying to copy cards from invalid cardLocation {}", cardLocation);
			break;
		}

		for (int i = 0; i < numberOfCardsToCopy; i++) {
			if (source.isEmpty()) {
				return;
			}
			context.getLogic().receiveCard(player.getId(), source.getRandom().clone());
		}
	}

}
