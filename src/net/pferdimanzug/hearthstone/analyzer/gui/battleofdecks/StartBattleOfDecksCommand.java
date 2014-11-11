package net.pferdimanzug.hearthstone.analyzer.gui.battleofdecks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroFactory;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class StartBattleOfDecksCommand extends SimpleCommand<GameNotification> {

	private static Logger logger = LoggerFactory.getLogger(StartBattleOfDecksCommand.class);

	private BattleResult result;

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
				for (Deck deck : battleConfig.getDecks()) {
					processedDecks.add(deck);
					for (Deck deck2 : battleConfig.getDecks()) {
						if (processedDecks.contains(deck2)) {
							continue;
						}
						PlayBatchTask task = new PlayBatchTask(deck, deck2, battleConfig.getBehaviour(), battleConfig.getNumberOfGames());
						Future<Void> future = executor.submit(task);
						futures.add(future);
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
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				//getFacade().sendNotification(GameNotification.SIMULATION_RESULT, result);
				logger.info("Battle of Decks finished");

			}
		});
		t.setDaemon(true);
		t.start();
	}

	private class PlayBatchTask implements Callable<Void> {

		private final Deck deck1;
		private final Deck deck2;
		private final IBehaviour behaviour;
		private final int numberOfGames;

		public PlayBatchTask(Deck deck1, Deck deck2, IBehaviour behaviour, int numberOfGames) {
			this.deck1 = deck1;
			this.deck2 = deck2;
			this.behaviour = behaviour;
			this.numberOfGames = numberOfGames;
		}

		@Override
		public Void call() throws Exception {
			BattleBatchResult batchResult = new BattleBatchResult(deck1, deck2, numberOfGames);
			for (int i = 0; i < numberOfGames; i++) {
				Hero hero1 = HeroFactory.createHero(deck1.getHeroClass());
				Player player1 = new Player("Player 1", hero1, deck1);
				player1.setBehaviour(behaviour.clone());

				Hero hero2 = HeroFactory.createHero(deck2.getHeroClass());
				Player player2 = new Player("Player 2", hero2, deck2);
				player2.setBehaviour(behaviour.clone());

				GameContext newGame = new GameContext(player1, player2, new GameLogic());
				newGame.play();

				batchResult.onGameEnded(newGame);

				synchronized (result) {
					result.onGameEnded(newGame);
				}

				newGame.dispose();

			}

			synchronized (result) {
				result.addBatchResult(batchResult);
				sendNotification(GameNotification.BATTLE_OF_DECKS_PROGRESS_UPDATE, result);
			}

			return null;
		}

	}

}
