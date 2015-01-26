package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class RemoveSecretsSpell extends Spell {
	
	public static SpellDesc create(TargetPlayer targetPlayer) {
		SpellDesc desc = new SpellDesc(RemoveSecretsSpell.class);
		desc.setTargetPlayer(targetPlayer);
		desc.setTarget(EntityReference.NONE);
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
