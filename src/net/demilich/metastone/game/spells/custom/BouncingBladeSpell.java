package net.demilich.metastone.game.spells.custom;

import java.util.Map;
import java.util.function.Predicate;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BouncingBladeSpell extends DamageSpell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(BouncingBladeSpell.class);
		arguments.put(SpellArg.RANDOM_TARGET, true);
		arguments.put(SpellArg.VALUE, 1);
		Predicate<Entity> targetFilter = entity -> {
			Actor actor = (Actor) entity;
			if (actor.isDead()) {
				return false;
			}

			if (actor.hasTag(GameTag.CANNOT_REDUCE_HP_BELOW_1) && actor.getHp() == 1) {
				return false;
			}

			return true;
		};
		arguments.put(SpellArg.FILTER, targetFilter);
		arguments.put(SpellArg.TARGET, EntityReference.ALL_MINIONS);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		if (target == null) {
			return;
		}

		super.onCast(context, player, desc, source, target);
		if (((Actor) target).isDead()) {
			return;
		}

		// player has commanding shout active; check if there are targets with
		// >1 hp
		if (player.getHero().hasTag(GameTag.CANNOT_REDUCE_HP_BELOW_1)) {
			boolean validTargets = false;
			for (Minion minion : player.getMinions()) {
				if (minion.getHp() > 1) {
					validTargets = true;
					break;
				}
			}
			if (!validTargets) {
				return;
			}
		}

		EntityReference sourceReference = source != null ? source.getReference() : null;
		context.getLogic().castSpell(player.getId(), desc, sourceReference, desc.getTarget());
	}

}
