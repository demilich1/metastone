package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.Secret;

public class AddSecretSpell extends Spell {


	public AddSecretSpell(Secret secret) {
		setCloneableData(secret);
	}

	public Secret getSecret() {
		return (Secret) getCloneableData()[0];
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		context.getLogic().playSecret(player, getSecret());
	}

}
