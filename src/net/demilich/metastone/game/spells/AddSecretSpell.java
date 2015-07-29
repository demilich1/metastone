package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.secrets.Secret;
import net.demilich.metastone.game.targeting.EntityReference;

public class AddSecretSpell extends Spell {

	public static SpellDesc create(Secret secret) {
		Map<SpellArg, Object> arguments = SpellDesc.build(AddSecretSpell.class);
		arguments.put(SpellArg.SECRET, secret);
		arguments.put(SpellArg.TARGET, EntityReference.FRIENDLY_HERO);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Secret secret = (Secret) desc.get(SpellArg.SECRET);
		context.getLogic().playSecret(player, secret);
	}

}
