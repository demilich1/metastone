package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellArg;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.Secret;

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
