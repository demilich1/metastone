package com.hiddenswitch.proto3.net.util;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.GameLogic;
import org.unitils.reflectionassert.ReflectionAssert;

public class SerializationTestContext extends GameContext {
	public SerializationTestContext() {
		super();
	}

	public SerializationTestContext(Player player1, Player player2, GameLogic logic, DeckFormat deckFormat) {
		super(player1, player2, logic, deckFormat);
	}

	@Override
	public void play() {
		init();
		while (!gameDecided()) {
			startTurn(activePlayer);
			while (playTurn()) {
				// Serialize and deserialize the game, testing that the game contexts are still equal (!)
				String s = Serialization.serialize(this);
				SerializationTestContext deserialized = Serialization.deserialize(s, SerializationTestContext.class);
				ReflectionAssert.assertReflectionEquals(this, deserialized);
			}
			if (getTurn() > GameLogic.TURN_LIMIT) {
				break;
			}
		}
		endGame();
	}
}
