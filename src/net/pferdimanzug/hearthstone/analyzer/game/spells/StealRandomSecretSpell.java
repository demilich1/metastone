package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.IGameEventListener;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.secrets.Secret;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class StealRandomSecretSpell extends Spell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(StealRandomSecretSpell.class);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Player opponent = context.getOpponent(player);
		List<IGameEventListener> secrets = context.getLogic().getSecrets(opponent);
		if (secrets.isEmpty()) {
			return;
		}
		Secret secret = (Secret) secrets.get(context.getLogic().random(secrets.size()));
		secret.setHost(player.getHero());
		secret.setOwner(player.getId());
		player.getSecrets().add(secret.getSource().getTypeId());
		opponent.getSecrets().remove((Integer) secret.getSource().getTypeId());
	}

}
