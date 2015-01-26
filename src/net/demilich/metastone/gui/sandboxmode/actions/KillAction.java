package net.demilich.metastone.gui.sandboxmode.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class KillAction extends GameAction {

	public KillAction() {
		setTargetRequirement(TargetSelection.MINIONS);
		setActionType(ActionType.BATTLECRY);
	}

	@Override
	public void execute(GameContext context, int playerId) {
		SpellDesc destroySpell = DestroySpell.create();
		destroySpell.setTarget(getTargetKey());
		context.getLogic().castSpell(playerId, destroySpell);
	}

	@Override
	public String getPromptText() {
		return "[Kill]";
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		return anotherAction instanceof KillAction;
	}

}
