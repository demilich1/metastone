package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class RemoveSecretsSpell extends Spell {
	
	public static SpellDesc create(TargetPlayer targetPlayer) {
		SpellDesc desc = new SpellDesc(RemoveSecretsSpell.class);
		desc.setTargetPlayer(targetPlayer);
		return desc;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		TargetPlayer targetPlayer = desc.getTargetPlayer();
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
