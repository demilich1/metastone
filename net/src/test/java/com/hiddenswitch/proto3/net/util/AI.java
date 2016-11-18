package com.hiddenswitch.proto3.net.util;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.behaviour.PlayRandomBehaviour;
import net.demilich.metastone.game.behaviour.threat.FeatureVector;
import net.demilich.metastone.game.behaviour.threat.GameStateValueBehaviour;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class AI extends PlayRandomBehaviour implements Serializable {
	private transient final AtomicInteger atomicInteger;

	public AI(AtomicInteger atomicInteger) {
		super();
		this.atomicInteger = atomicInteger;
	}

	@Override
	public void onGameOver(GameContext context, int playerId, int winningPlayerId) {
		atomicInteger.incrementAndGet();
	}
}
