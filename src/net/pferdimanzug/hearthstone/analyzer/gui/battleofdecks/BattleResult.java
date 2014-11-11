package net.pferdimanzug.hearthstone.analyzer.gui.battleofdecks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.Statistic;

public class BattleResult {

	private final int numberOfGames;
	private final HashMap<String, GameStatistics> deckResults = new HashMap<String, GameStatistics>();
	private final List<BattleBatchResult> batchResults = new ArrayList<BattleBatchResult>();

	public BattleResult(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	public synchronized void onGameEnded(GameContext result) {
		for (Player player : result.getPlayers()) {
			updateStats(player);
		}
	}

	private void updateStats(Player player) {
		String deckName = player.getDeckName();
		if (!deckResults.containsKey(deckName)) {
			deckResults.put(deckName, new GameStatistics());
		}
		GameStatistics stats = deckResults.get(deckName);
		stats.merge(player.getStatistics());
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}
	
	public synchronized void addBatchResult(BattleBatchResult batchResult) {
		batchResults.add(batchResult);
	}

	public synchronized List<BattleBatchResult> getBatchResults() {
		return new ArrayList<BattleBatchResult>(batchResults);
	}
	
	public synchronized List<BattleDeckResult> getDeckResults() {
		List<BattleDeckResult> resultList = new ArrayList<BattleDeckResult>();
		for (String deckName : deckResults.keySet()) {
			
			BattleDeckResult deckResult = new BattleDeckResult(deckName, deckResults.get(deckName));
			resultList.add(deckResult);
		}
		return resultList;
	}

}
