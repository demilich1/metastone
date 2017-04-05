package net.demilich.metastone.game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.IGameEventListener;
import net.demilich.metastone.game.spells.trigger.types.Secret;
import net.demilich.metastone.game.targeting.EntityReference;

public class StealRandomSecretSpell extends Spell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(StealRandomSecretSpell.class);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Player opponent = context.getOpponent(player);
		List<IGameEventListener> secrets = context.getLogic().getSecrets(opponent);

		if (secrets.isEmpty()) {
			return;
		}

		// try to steal a secret which we do not own yet
		List<Secret> validSecrets = new ArrayList<>();
		for (IGameEventListener trigger : secrets) {
			Secret secret = (Secret) trigger;
			if (!player.getSecrets().contains(secret.getSource().getCardId()) && player.getSecrets().size() < GameLogic.MAX_SECRETS) {
				validSecrets.add(secret);
			}
		}

		if (!validSecrets.isEmpty()) {
			Secret secret = validSecrets.get(context.getLogic().random(validSecrets.size()));
			secret.setHost(player.getHero());
			secret.setOwner(player.getId());
			player.getSecrets().add(secret.getSource().getCardId());
			opponent.getSecrets().remove(secret.getSource().getCardId());
		} else {
			// no valid secret to steal; instead destroy one for the opponent at
			// least
			Secret secret = (Secret) secrets.get(context.getLogic().random(secrets.size()));
			context.removeTrigger(secret);
			opponent.getSecrets().remove(secret.getSource().getCardId());
		}

	}

}
