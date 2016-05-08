package net.demilich.metastone.gui.battleofdecks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

public class StartBattleOfDecksCommand extends SimpleCommand<GameNotification> {

	private class PlayGameTask implements Callable<Void> {

		private final PlayerConfig player1Config;
		private final PlayerConfig player2Config;
		private final BattleBatchResult batchResult;

		public PlayGameTask(Deck deck1, Deck deck2, IBehaviour behaviour, BattleBatchResult batchResult) {
			this.player1Config = new PlayerConfig(deck1, behaviour);
			player1Config.setName("Player 1");
			this.player2Config = new PlayerConfig(deck2, behaviour);
			player2Config.setName("Player 2");
			this.batchResult = batchResult;
		}

		@Override
		public Void call() throws Exception {
			Player player1 = new Player(player1Config);
			Player player2 = new Player(player2Config);
	
			DeckFormat deckFormat = new DeckFormat();
			for (CardSet set : CardSet.values()) {
				deckFormat.addSet(set);
			}

			GameContext newGame = new GameContext(player1, player2, new GameLogic(), deckFormat);
			newGame.play();

			batchResult.onGameEnded(newGame);
			result.onGameEnded(newGame);

			periodicUpdate();
			newGame.dispose();

			return null;
		}

	}

	private static Logger logger = LoggerFactory.getLogger(StartBattleOfDecksCommand.class);
	private BattleResult result;

	private long lastUpdate;

	@Override
	public void execute(INotification<GameNotification> notification) {
		BattleConfig battleConfig = (BattleConfig) notification.getBody();
		result = new BattleResult(battleConfig.getNumberOfGames());

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				logger.info("Battle of Decks started");
				ExecutorService executor = Executors.newWorkStealingPool();

				List<Future<Void>> futures = new ArrayList<Future<Void>>();
				HashSet<Deck> processedDecks = new HashSet<>();
				for (Deck deck1 : battleConfig.getDecks()) {
					processedDecks.add(deck1);
					for (Deck deck2 : battleConfig.getDecks()) {
						if (processedDecks.contains(deck2)) {
							continue;
						}
						BattleBatchResult batchResult = new BattleBatchResult(deck1, deck2, battleConfig.getNumberOfGames());
						result.addBatchResult(batchResult);

						for (int i = 0; i < battleConfig.getNumberOfGames(); i++) {
							PlayGameTask task = new PlayGameTask(deck1, deck2, battleConfig.getBehaviour(), batchResult);
							Future<Void> future = executor.submit(task);
							futures.add(future);
						}
					}
				}

				executor.shutdown();
				boolean completed = false;
				while (!completed) {
					completed = true;
					for (Future<Void> future : futures) {
						if (!future.isDone()) {
							completed = false;
							continue;
						}
						try {
							future.get();
						} catch (InterruptedException | ExecutionException e) {
							logger.error(ExceptionUtils.getStackTrace(e));
							e.printStackTrace();
							System.exit(-1);
						}
					}
					futures.removeIf(future -> future.isDone());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				// getFacade().sendNotification(GameNotification.SIMULATION_RESULT,
				// result);
				logger.info("Battle of Decks finished");

			}
		});
		t.setDaemon(true);
		t.start();
	}

	private void periodicUpdate() {
		if (System.currentTimeMillis() - lastUpdate > 1000) {
			sendNotification(GameNotification.BATTLE_OF_DECKS_PROGRESS_UPDATE, result);
			lastUpdate = System.currentTimeMillis();
		}
	}

}
