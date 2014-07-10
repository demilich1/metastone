package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class DrawCardSpell extends Spell {

	private final int numberOfCards;
	private final TargetPlayer targetPlayer;
	private final IValueProvider drawModifier;

	public DrawCardSpell() {
		this(1);
	}

	public DrawCardSpell(int numberOfCards) {
		this(numberOfCards, TargetPlayer.SELF);
	}

	public DrawCardSpell(int numberOfCards, TargetPlayer targetPlayer) {
		this(null, numberOfCards, targetPlayer);
	}

	private DrawCardSpell(IValueProvider drawModifier, int numberOfCards, TargetPlayer targetPlayer) {
		this.drawModifier = drawModifier;
		this.numberOfCards = numberOfCards;
		this.targetPlayer = targetPlayer;
		setTarget(EntityReference.NONE);
	}

	public DrawCardSpell(IValueProvider drawModifier, TargetPlayer targetPlayer) {
		this(drawModifier, 0, targetPlayer);
	}

	private void draw(GameContext context, Player player) {
		int cardCount = drawModifier != null ? drawModifier.provideValue(context, player, null) : numberOfCards;
		for (int i = 0; i < cardCount; i++) {
			context.getLogic().drawCard(player.getId());
		}
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
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
}
