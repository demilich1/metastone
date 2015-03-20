package net.demilich.metastone.game.spells.custom;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ShadowflameSpell extends DestroySpell {

	public static SpellDesc create() {
		Map<SpellArg, Object> arguments = SpellDesc.build(ShadowflameSpell.class);
		return new SpellDesc(arguments);
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		Minion targetMinion = (Minion) target;

		// deal attack damage to all enemy minions
		SpellDesc damageSpell = DamageSpell.create(targetMinion.getAttack());
		EntityReference sourceReference = source != null ? source.getReference() : null;
		context.getLogic().castSpell(player.getId(), damageSpell, sourceReference, EntityReference.ENEMY_MINIONS);

		// destroy minion
		super.onCast(context, player, desc, source, target);
	}

}
