package net.pferdimanzug.hearthstone.analyzer.statistics;

import java.util.HashMap;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticsView {
	
	private static Logger logger = LoggerFactory.getLogger(StatisticsView.class);
	
	private int gamesPlayed;
	private HashMap<String, Integer> gamesWon = new HashMap<>();
	private long startTime;
	
	public void onBatchStart() {
		gamesPlayed = 0;
		gamesWon.clear();
		startTime = System.currentTimeMillis();
	}
	
	public void onGameOver(GameContext context) {
		gamesPlayed++;
		registerWinner(context);
	}
	
	private void registerWinner(GameContext context) {
		Player winner = context.getWinner();
		String key = winner.getName();
		if (!gamesWon.containsKey(key)) {
			gamesWon.put(key, 0);
		}
		int amount = gamesWon.get(key);
		gamesWon.put(key, amount + 1);
	}
	
	public void onBatchStop() {
		logger.info("Games played: {}", gamesPlayed);
		long duration = System.currentTimeMillis() - startTime;
		logger.info("Duration: {}ms", duration);
		for (String name : gamesWon.keySet()) {
			logger.info("Player " + name + " won " + gamesWon.get(name) + " games");
		}
	}

}
