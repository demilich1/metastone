package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.actions;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.EntityEditor;

public class EditEntityAction extends GameAction {

	public EditEntityAction() {
		setTargetRequirement(TargetSelection.ANY);
		setActionType(ActionType.BATTLECRY);
	}

	@Override
	public void execute(GameContext context, int playerId) {
		Entity entity = context.resolveSingleTarget(getTargetKey());
		EntityEditor editor = new EntityEditor(entity, result -> ApplicationFacade.getInstance().sendNotification(GameNotification.UPDATE_SANDBOX_STATE, context));
		ApplicationFacade.getInstance().sendNotification(GameNotification.SHOW_MODAL_DIALOG, editor);
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
