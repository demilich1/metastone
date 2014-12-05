package net.pferdimanzug.hearthstone.analyzer.gui.battleofdecks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.statistics.GameStatistics;

public class BattleResult {

	private final int numberOfGames;
	private final HashMap<String, GameStatistics> deckResults = new HashMap<String, GameStatistics>();
	private final List<BattleBatchResult> batchResults = new ArrayList<BattleBatchResult>();

	public BattleResult(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}

	public void addBatchResult(BattleBatchResult batchResult) {
		synchronized (batchResults) {
			batchResults.add(batchResult);
		}
	}

	public List<BattleBatchResult> getBatchResults() {
		synchronized (batchResults) {
			return new ArrayList<BattleBatchResult>(batchResults);
		}
	}

	public List<BattleDeckResult> getDeckResults() {
		List<BattleDeckResult> resultList = new ArrayList<BattleDeckResult>();
		synchronized (deckResults) {
			for (String deckName : deckResults.keySet()) {
				BattleDeckResult deckResult = new BattleDeckResult(deckName, deckResults.get(deckName));
				resultList.add(deckResult);
			}	
		}
		return resultList;
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public void onGameEnded(GameContext result) {
		for (Player player : result.getPlayers()) {
			updateStats(player);
		}
	}

	private void updateStats(Player player) {
		String deckName = player.getDeckName();
		synchronized (deckResults) {
			if (!deckResults.containsKey(deckName)) {
				deckResults.put(deckName, new GameStatistics());
			}
			GameStatistics stats = deckResults.get(deckName);
			stats.merge(player.getStatistics());
		}
	}

}
