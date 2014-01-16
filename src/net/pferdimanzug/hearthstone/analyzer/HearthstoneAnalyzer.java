package net.pferdimanzug.hearthstone.analyzer;

import java.io.IOException;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.DebugDecks;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.MinMaxBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.PlayRandomBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Guldan;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Thrall;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Valeera;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

public class HearthstoneAnalyzer {

	public static void main(String[] args) {
		ApplicationFacade facade = (ApplicationFacade) ApplicationFacade.getInstance();
		HearthstoneAnalyzer instance = new HearthstoneAnalyzer();
		facade.startUp(instance);
		facade.sendNotification(GameNotification.BATCH_START);
		for (int i = 0; i < 100; i++) {
			instance.launchDebugGame();	
		}
		facade.sendNotification(GameNotification.BATCH_STOP);
		
		/*
		 try {
			DevCheckCardCompleteness.cardListFromImages("D:\\projects\\images\\hearthstone\\cards\\");
			 //DevCheckCardCompleteness.compareClassesWithCardList("D:\\projects\\java\\HearthstoneAnalyzer\\src\\net\\pferdimanzug\\hearthstone\\analyzer\\game\\cards\\concrete");
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
	}
	
	private void launchDebugGame() {
		Hero hero1 = new Guldan();
		Player player1 = new Player("Human", hero1, DebugDecks.getRandomDeck(hero1.getHeroClass()));
		player1.setBehaviour(new MinMaxBehaviour());
		Hero hero2 = new Garrosh();
		Player player2 = new Player("Bot", hero2, DebugDecks.getRandomDeck(hero2.getHeroClass()));
		player2.setBehaviour(new PlayRandomBehaviour());
		GameContext newGame = new GameContext(player1, player2, new GameLogic());
		ApplicationFacade.getInstance().sendNotification(GameNotification.START_GAME, newGame);
		//ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_STATE_UPDATE, newGame);
	}

	private void printCardsForDatabase() {
		try {
			String path = "./src/" + Card.class.getPackage().getName().replace(".", "/") + "/concrete";
			System.out.println("Path: " + path);
			DevCheckCardCompleteness.printImplementedCards(path, "cards.add(new %s());");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
