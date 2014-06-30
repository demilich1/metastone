package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class RemoveSecretsSpell extends Spell {

	private final TargetPlayer targetPlayer;

	public RemoveSecretsSpell(TargetPlayer targetPlayer) {
		this.targetPlayer = targetPlayer;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			context.getLogic().removeSecrets(player);
			context.getLogic().removeSecrets(opponent);
			break;
		case OPPONENT:
			context.getLogic().removeSecrets(opponent);
			break;
		case SELF:
			context.getLogic().removeSecrets(player);
			break;
		}
	}

}
