package com.hiddenswitch.proto3.net.common;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.spells.trigger.TriggerManager;
import net.demilich.metastone.game.targeting.IdFactory;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.HashMap;

public class GameState implements Serializable {
	public final Player player1;
	public final Player player2;
	public final HashMap<Environment, Object> environment;
	public final TriggerManager triggerManager;
	public final int currentId;
	public final TurnState turnState;
	public final long timestamp;

	public GameState(GameContext fromContext) {
		this(fromContext, fromContext.getTurnState());
	}

	public GameState(GameContext fromContext, TurnState turnState) {
		this.timestamp = System.nanoTime();
		player1 = fromContext.getPlayer1();
		player2 = fromContext.getPlayer2();
		environment = SerializationUtils.clone(fromContext.getEnvironment());
		currentId = fromContext.getLogic().getIdFactory().getInternalId();
		triggerManager = SerializationUtils.clone(fromContext.getTriggerManager());
		this.turnState = turnState;
	}

	public boolean isValid() {
		return player1 != null
				&& player2 != null
				&& environment != null
				&& triggerManager != null
				&& turnState != null;
	}
}
