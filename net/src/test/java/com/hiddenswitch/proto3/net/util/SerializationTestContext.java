package com.hiddenswitch.proto3.net.util;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.GameLogic;
import org.apache.commons.lang3.builder.EqualsBuilder;

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
				GameContext toSerialize = (GameContext) this;
				String s = Serialization.serialize(toSerialize);
				GameContext deserialized = Serialization.deserialize(s, GameContext.class);
				EqualsBuilder.reflectionEquals(toSerialize, deserialized);
			}
			if (getTurn() > GameLogic.TURN_LIMIT) {
				break;
			}
		}
		endGame();
	}
}
