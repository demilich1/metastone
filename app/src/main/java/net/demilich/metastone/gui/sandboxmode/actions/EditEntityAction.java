package net.demilich.metastone.gui.sandboxmode.actions;

import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.targeting.TargetSelection;
import net.demilich.metastone.gui.sandboxmode.EntityEditor;

public class EditEntityAction extends GameAction {

	public EditEntityAction() {
		setTargetRequirement(TargetSelection.ANY);
		setActionType(ActionType.SYSTEM);
	}

	@Override
	public void execute(GameContext context, int playerId) {
		Entity entity = context.resolveSingleTarget(getTargetKey());
		EntityEditor editor = new EntityEditor(entity,
				result -> NotificationProxy.sendNotification(GameNotification.UPDATE_SANDBOX_STATE, context));
		NotificationProxy.sendNotification(GameNotification.SHOW_MODAL_DIALOG, editor);
	}

	@Override
	public String getPromptText() {
		return "[Edit entity]";
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		return false;
	}

}
