package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class MetaSpell extends Spell {

	protected final Spell[] spells;

	public MetaSpell(Spell... spells) {
		this.spells = spells;
	}

	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		for (Spell spell : spells) {
			if (!spell.hasPredefinedTarget()) {
				spell.setTarget(getTarget());
			}
			spell.setSource(getSource());
			context.getLogic().castSpell(player.getId(), spell);
		}
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
	}

	@Override
	public void setApplySpellpower(boolean applySpellpower) {
		super.setApplySpellpower(applySpellpower);
		for (Spell spell : spells) {
			spell.setApplySpellpower(applySpellpower);
		}
	}

}
