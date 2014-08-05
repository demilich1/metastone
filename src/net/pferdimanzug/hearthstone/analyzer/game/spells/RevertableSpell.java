package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public abstract class RevertableSpell extends Spell {

	public RevertableSpell() {
		this(null);
	}

	public RevertableSpell(GameEventTrigger revertTrigger) {
		this(revertTrigger, null);
	}
	
	public RevertableSpell(GameEventTrigger revertTrigger, GameEventTrigger secondRevertTrigger) {
		setCloneableData(revertTrigger, secondRevertTrigger);
	}

	protected abstract Spell getReverseSpell();

	public GameEventTrigger getRevertTrigger() {
		return (GameEventTrigger) getCloneableData()[0];
	}

	public GameEventTrigger getSecondRevertTrigger() {
		return (GameEventTrigger) getCloneableData()[1];
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		if (getRevertTrigger() != null) {
			Spell revert = getReverseSpell();
			revert.setTarget(target.getReference());
			SpellTrigger removeTrigger = new SpellTrigger(getRevertTrigger(), getSecondRevertTrigger(), revert, true);
			context.getLogic().addGameEventListener(player, removeTrigger, target);
		}
	}

}
