package com.hiddenswitch.proto3.net.common;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.targeting.IdFactory;

import java.io.Serializable;
import java.util.HashMap;

public class GameState implements Serializable {
	public final Player player1;
	public final Player player2;
	public final HashMap<Environment, Object> environment;
	public final IdFactory idFactory;
	public final TurnState turnState;

	public GameState(GameContext fromContext) {
		player1 = fromContext.getPlayer1();
		player2 = fromContext.getPlayer2();
		environment = fromContext.getEnvironment();
		idFactory = fromContext.getLogic().getIdFactory();
		turnState = fromContext.getTurnState();
	}

	public GameState(GameContext fromContext, TurnState turnState) {
		player1 = fromContext.getPlayer1();
		player2 = fromContext.getPlayer2();
		environment = fromContext.getEnvironment();
		idFactory = fromContext.getLogic().getIdFactory();
		this.turnState = turnState;
	}

	public GameState(Player player1, Player player2, HashMap<Environment, Object> environment, IdFactory idFactory, TurnState turnState) {
		this.player1 = player1;
		this.player2 = player2;
		this.environment = environment;
		this.idFactory = idFactory;
		this.turnState = turnState;
	}
}
