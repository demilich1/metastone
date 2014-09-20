package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public abstract class Behaviour implements IBehaviour {

	public IBehaviour clone() {
		try {
			return (IBehaviour)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onGameOver(GameContext context, int playerId, int winningPlayerId) {
	}
	
}
