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
		context.getLogic().removeSecrets(player);
	}

}
