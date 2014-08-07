package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class VoidTerrorBuffSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(VoidTerrorBuffSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int attackBonus = 0;
		int hpBonus = 0;
		for (Entity adjacent : context.getAdjacentMinions(player, target.getReference())) {
			Minion minion = (Minion) adjacent;
			attackBonus += minion.getAttack();
			hpBonus += minion.getHp();
		}
		SpellDesc buffSpell = BuffSpell.create(attackBonus, hpBonus);
		buffSpell.setTarget(target.getReference());
		context.getLogic().castSpell(player.getId(), buffSpell);
	}

}
