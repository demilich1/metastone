package com.hiddenswitch.proto3.net.behaviour;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class NullBehaviour implements IBehaviour {
	@Override
	public IBehaviour clone() {
		return new NullBehaviour();
	}

	@Override
	public String getName() {
		return "(Remote Player)";
	}

	@Override
	public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
		return new ArrayList<>();
	}

	@Override
	public void onGameOver(GameContext context, int playerId, int winningPlayerId) {
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		return null;
	}
}
