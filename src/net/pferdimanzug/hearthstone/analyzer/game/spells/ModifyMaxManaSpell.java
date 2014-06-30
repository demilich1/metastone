package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class ModifyMaxManaSpell extends Spell {

	private final TargetPlayer targetPlayer;
	private final int value;

	public ModifyMaxManaSpell() {
		this(1, TargetPlayer.SELF);
	}

	public ModifyMaxManaSpell(int value, TargetPlayer targetPlayer) {
		this.value = value;
		this.targetPlayer = targetPlayer;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case SELF:
			context.getLogic().modifyMaxMana(player, value);
			break;
		case OPPONENT:
			context.getLogic().modifyMaxMana(opponent, value);
			break;
		case BOTH:
			context.getLogic().modifyMaxMana(player, value);
			context.getLogic().modifyMaxMana(opponent, value);
			break;
		}

	}

}
