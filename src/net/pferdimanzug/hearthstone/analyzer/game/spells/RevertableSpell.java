package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public abstract class RevertableSpell extends Spell {

	private final GameEventTrigger revertTrigger;
	private final GameEventTrigger secondRevertTrigger;

	public RevertableSpell() {
		this(null);
	}

	public RevertableSpell(GameEventTrigger revertTrigger) {
		this(revertTrigger, null);
	}
	
	public RevertableSpell(GameEventTrigger revertTrigger, GameEventTrigger secondRevertTrigger) {
		this.revertTrigger = revertTrigger;
		this.secondRevertTrigger = secondRevertTrigger;
	}

	protected abstract Spell getReverseSpell();

	public GameEventTrigger getRevertTrigger() {
		return revertTrigger != null ? revertTrigger.clone() : null;
	}

	public GameEventTrigger getSecondRevertTrigger() {
		return secondRevertTrigger != null ? secondRevertTrigger.clone() : null;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		if (getRevertTrigger() != null) {
			Spell revert = getReverseSpell().clone();
			revert.setTarget(target.getReference());
			SpellTrigger removeTrigger = new SpellTrigger(getRevertTrigger(), getSecondRevertTrigger(), revert, true);
			context.getLogic().addGameEventListener(player, removeTrigger, target);
		}
	}

}
