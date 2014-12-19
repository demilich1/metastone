package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
