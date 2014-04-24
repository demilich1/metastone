package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DrawCardSpell extends Spell {

	private int numberOfCards;
	private final TargetPlayer targetPlayer;

	public DrawCardSpell() {
		this(1);
	}

	public DrawCardSpell(int numberOfCards) {
		this(numberOfCards, TargetPlayer.SELF);
	}

	public DrawCardSpell(int numberOfCards, TargetPlayer targetPlayer) {
		this.numberOfCards = numberOfCards;
		this.targetPlayer = targetPlayer;
		setTarget(EntityReference.NONE);
	}

	public int getNumberOfCards() {
		return numberOfCards;
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			draw(context, player);
			draw(context, opponent);
			break;
		case OPPONENT:
			draw(context, opponent);
			break;
		case SELF:
			draw(context, player);
			break;
		default:
			break;
		}
	}

	private void draw(GameContext context, Player player) {
		for (int i = 0; i < numberOfCards; i++) {
			context.getLogic().drawCard(player.getId());
		}
	}

	protected void setNumberOfCards(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

}
