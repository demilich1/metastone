package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class ExplosiveShotSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(ExplosiveShotSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		SpellDesc primary = DamageSpell.create(5);
		primary.setSource(desc.getSource());
		SpellDesc secondary = DamageSpell.create(2);
		secondary.setSource(desc.getSource());
		primary.setTarget(desc.getTarget());
		context.getLogic().castSpell(player.getId(), primary);

		for (Entity adjacent : context.getAdjacentMinions(player, target.getReference())) {
			secondary.setTarget(adjacent.getReference());
			context.getLogic().castSpell(player.getId(), secondary);
		}
	}

}