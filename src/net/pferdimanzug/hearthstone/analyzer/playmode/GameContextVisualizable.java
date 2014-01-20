package net.pferdimanzug.hearthstone.analyzer.playmode;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.logic.IGameLogic;

public class GameContextVisualizable extends GameContext {

	public GameContextVisualizable(Player player1, Player player2, IGameLogic logic) {
		super(player1, player2, logic);
	}

	@Override
	protected void onGameStateChanged() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_STATE_UPDATE, this);
	}
	
	

}
