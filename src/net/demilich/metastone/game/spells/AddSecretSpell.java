package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;

public class AddSecretSpell extends Spell {
	
	public static SpellDesc create(Secret secret) {
		SpellDesc desc = new SpellDesc(AddSecretSpell.class);
		desc.set(SpellArg.SECRET, secret);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Secret secret = (Secret) desc.get(SpellArg.SECRET);
		context.getLogic().playSecret(player, secret);
	}

}
