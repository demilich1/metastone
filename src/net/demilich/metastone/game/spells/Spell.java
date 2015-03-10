package net.demilich.metastone.game.spells;

import java.util.List;
import java.util.function.Predicate;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public abstract class Spell {

	public void cast(GameContext context, Player player, SpellDesc desc, List<Entity> targets) {
		if (targets == null) {
			castForPlayer(context, player, desc, null);
			return;
		}
		
		@SuppressWarnings("unchecked")
		Predicate<Entity> targetFilter = (Predicate<Entity>) desc.get(SpellArg.ENTITY_FILTER);
		List<Entity> validTargets = SpellUtils.getValidTargets(targets, targetFilter);
		if (validTargets.size() > 0 && desc.getBool(SpellArg.RANDOM_TARGET)) {
			Entity target = SpellUtils.getRandomTarget(validTargets);
			castForPlayer(context, player, desc, target);
			onCast(context, player, desc, target);
		} else {
			for (Entity target : validTargets) {
				castForPlayer(context, player, desc, target);
			}
		}
	}
	
	private void castForPlayer(GameContext context, Player player, SpellDesc desc, Entity target) {
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		if (targetPlayer == null) {
			targetPlayer = TargetPlayer.SELF;
		}
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			onCast(context, player, desc, target);
			onCast(context, opponent, desc, target);
			break;
		case OPPONENT:
			onCast(context, opponent, desc, target);
			break;
		case SELF:
			onCast(context, player, desc, target);
			break;
		default:
			break;
		}
	}

	protected abstract void onCast(GameContext context, Player player, SpellDesc desc, Entity target);

	@Override
	public String toString() {
		return "[SPELL " + getClass().getSimpleName() + "]";
	}

}
