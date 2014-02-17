package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;

public class MetaSpell extends Spell {
	
	protected final Spell spell1;
	protected final Spell spell2;

	public MetaSpell(Spell spell1, Spell spell2) {
		this.spell1 = spell1;
		this.spell2 = spell2;
	}

	@Override
	public void cast(GameContext context, Player player, List<Actor> targets) {
		if (!spell1.hasPredefinedTarget()) {
			spell1.setTarget(getTarget());
		}
		context.getLogic().castSpell(player.getId(), spell1);
		if (!spell2.hasPredefinedTarget()) {
			spell2.setTarget(getTarget());
		}
		context.getLogic().castSpell(player.getId(), spell2);
	}

	@Override
	protected void onCast(GameContext context, Player player, Actor target) {
	}

	@Override
	public void setApplySpellpower(boolean applySpellpower) {
		super.setApplySpellpower(applySpellpower);
		spell1.setApplySpellpower(applySpellpower);
		spell2.setApplySpellpower(applySpellpower);
	}
	
}
