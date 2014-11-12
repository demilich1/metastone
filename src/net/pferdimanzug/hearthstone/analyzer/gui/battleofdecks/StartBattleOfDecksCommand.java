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

	private class PlayGameTask implements Callable<Void> {

		private final Deck deck1;
		private final Deck deck2;
		private final IBehaviour behaviour;
		private final BattleBatchResult batchResult;

		public PlayGameTask(Deck deck1, Deck deck2, IBehaviour behaviour, BattleBatchResult batchResult) {
			this.deck1 = deck1;
			this.deck2 = deck2;
			this.behaviour = behaviour;
			this.batchResult = batchResult;
		}

		@Override
		public Void call() throws Exception {

			Hero hero1 = HeroFactory.createHero(deck1.getHeroClass());
			Player player1 = new Player("Player 1", hero1, deck1);
			player1.setBehaviour(behaviour.clone());

			Hero hero2 = HeroFactory.createHero(deck2.getHeroClass());
			Player player2 = new Player("Player 2", hero2, deck2);
			player2.setBehaviour(behaviour.clone());

			GameContext newGame = new GameContext(player1, player2, new GameLogic());
			newGame.play();

			batchResult.onGameEnded(newGame);
			result.onGameEnded(newGame);

			periodicUpdate();
			newGame.dispose();

			return null;
		}

	}

}
