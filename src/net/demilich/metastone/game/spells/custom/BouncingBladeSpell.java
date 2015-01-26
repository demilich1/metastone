package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class BouncingBladeSpell extends DamageSpell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(BouncingBladeSpell.class);
		desc.pickRandomTarget(true);
		desc.set(SpellArg.DAMAGE, 1);
		desc.setTargetFilter(entity -> {
			Actor actor = (Actor) entity;
			if (actor.isDead()) {
				return false;
			}

			if (actor.hasStatus(GameTag.CANNOT_REDUCE_HP_BELOW_1) && actor.getHp() == 1) {
				return false;
			}

			return true;
		});
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		if (target == null) {
			return;
		}

		super.onCast(context, player, desc, target);
		if (((Actor) target).isDead()) {
			return;
		}

		// player has commanding shout active; check if there are targets with
		// >1 hp
		if (player.getHero().hasStatus(GameTag.CANNOT_REDUCE_HP_BELOW_1)) {
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

		context.getLogic().castSpell(player.getId(), desc);
	}

}
