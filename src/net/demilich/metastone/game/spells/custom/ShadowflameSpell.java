package net.demilich.metastone.game.spells.custom;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class ShadowflameSpell extends DestroySpell {

	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ShadowflameSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Minion targetMinion = (Minion) target;

		// deal attack damage to all enemy minions
		SpellDesc damageSpell = DamageSpell.create(targetMinion.getAttack());
		damageSpell.setSourceEntity(desc.getSourceEntity());
		damageSpell.setTarget(EntityReference.ENEMY_MINIONS);
		context.getLogic().castSpell(player.getId(), damageSpell);

		// destroy minion
		super.onCast(context, player, desc, target);
	}

}
