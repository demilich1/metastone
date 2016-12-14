package com.hiddenswitch.cardsgen.functions;

import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.decks.DeckCatalogue;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.statistics.SimulationResult;
import net.demilich.metastone.utils.Tuple;
import net.demilich.nittygrittymvc.Notification;
import org.apache.spark.api.java.function.Function;


public class Simulator implements Function<GameConfig, SimulationResult> {
	@Override
	public SimulationResult call(GameConfig gameConfig) throws Exception {
		CardCatalogue.loadCardsFromPackage();

		SimulationResult result = new SimulationResult(gameConfig);

		PlayerConfig playerConfig1 = gameConfig.getPlayerConfig1();
		PlayerConfig playerConfig2 = gameConfig.getPlayerConfig2();

		DeckFormat deckFormat = gameConfig.getDeckFormat();

		for (int i = 0; i < gameConfig.getNumberOfGames(); i++) {
			Player player1 = new Player(playerConfig1);
			Player player2 = new Player(playerConfig2);
			final GameLogic logic = new GameLogic();
			logic.setLoggingEnabled(false);
			GameContext newGame = new GameContext(player1, player2, logic, deckFormat);
			try {
				newGame.play();

				result.getPlayer1Stats().merge(newGame.getPlayer1().getStatistics());
				result.getPlayer2Stats().merge(newGame.getPlayer2().getStatistics());
				result.calculateMetaStatistics();
			} finally {
				newGame.dispose();
			}
		}

		return result;
	}
}
