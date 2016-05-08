package net.demilich.metastone.game.spells;

import java.util.List;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;

public abstract class Spell {

	public void cast(GameContext context, Player player, SpellDesc desc, Entity source, List<Entity> targets) {
		// no target specified, cast the spell once with target NULL
		if (targets == null) {
			castForPlayer(context, player, desc, source, null);
			return;
		}

		EntityFilter targetFilter = desc.getEntityFilter();
		List<Entity> validTargets = SpellUtils.getValidTargets(context, player, targets, targetFilter);
		// there is at least one valid target and the RANDOM_TARGET flag is set,
		// pick one randomly
		if (validTargets.size() > 0 && desc.getBool(SpellArg.RANDOM_TARGET)) {
			Entity target = SpellUtils.getRandomTarget(validTargets);
			castForPlayer(context, player, desc, source, target);
		} else {
			// there is at least one target and RANDOM_TARGET flag is not set,
			// cast in on all targets

			for (Entity target : validTargets) {
				context.getEnvironment().put(Environment.SPELL_TARGET, target.getReference());
				castForPlayer(context, player, desc, source, target);
				context.getEnvironment().remove(Environment.SPELL_TARGET);
			}
		}
	}

	private void castForPlayer(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		if (targetPlayer == null) {
			targetPlayer = TargetPlayer.SELF;
		}
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			onCast(context, player, desc, source, target);
			onCast(context, opponent, desc, source, target);
			break;
		case OPPONENT:
			onCast(context, opponent, desc, source, target);
			break;
		case SELF:
			onCast(context, player, desc, source, target);
			break;
		case OWNER:
			onCast(context, context.getPlayer(target.getOwner()), desc, source, target);
			break;
		case ACTIVE:
			onCast(context, context.getActivePlayer(), desc, source, target);
			break;
		case INACTIVE:
			onCast(context, context.getOpponent(context.getActivePlayer()), desc, source, target);
			break;
		default:
			break;
		}
	}

	protected abstract void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target);

	@Override
	public String toString() {
		return "[SPELL " + getClass().getSimpleName() + "]";
	}

}
