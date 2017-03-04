package com.hiddenswitch.proto3.net.util;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.behaviour.PlayRandomBehaviour;
import net.demilich.metastone.game.behaviour.threat.FeatureVector;
import net.demilich.metastone.game.behaviour.threat.GameStateValueBehaviour;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class AI extends GameStateValueBehaviour implements Serializable {

	public AI() {
		super(FeatureVector.getFittest(), "Test");
	}

	@Override
	public void onGameOver(GameContext context, int playerId, int winningPlayerId) {
	}
}
