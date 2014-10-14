package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.actions;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SetManaAction extends GameAction {

	private final int targetPlayerId;
	private final int mana;

	public SetManaAction(int playerId, int mana) {
		this.targetPlayerId = playerId;
		this.mana = mana;
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public void execute(GameContext context, int playerId) {
		Player player = context.getPlayer(targetPlayerId);
		player.setMana(mana);
	}

	@Override
	public String getPromptText() {
		return "[SetMana]";
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		return false;
	}
	

}
