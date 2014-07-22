package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.Secret;

public class AddSecretSpell extends Spell {

	private final Secret secret;

	public AddSecretSpell(Secret secret) {
		this.secret = secret;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		context.getLogic().playSecret(player, getSecret());
	}

	public Secret getSecret() {
		return secret;
	}

}
