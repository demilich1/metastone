package net.pferdimanzug.hearthstone.analyzer;

import java.io.IOException;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.DebugDecks;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.PlayRandomBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Anduin;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Guldan;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Thrall;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Uther;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Valeera;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

public class HearthstoneAnalyzer {

	public static void main(String[] args) {
		ApplicationFacade facade = (ApplicationFacade) ApplicationFacade.getInstance();
		HearthstoneAnalyzer instance = new HearthstoneAnalyzer();
		facade.startUp(instance);
		facade.sendNotification(GameNotification.BATCH_START);
		for (int i = 0; i < 1; i++) {
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
		Player player1 = new Player("Human", new Garrosh(), DebugDecks.getRandomDeck(HeroClass.WARRIOR));
		player1.setBehaviour(new PlayRandomBehaviour(player1));
		Player player2 = new Player("Bot", new Guldan(), DebugDecks.getRandomDeck(HeroClass.WARLOCK));
		player2.setBehaviour(new PlayRandomBehaviour(player2));
		GameLogic logic = new GameLogic();
		GameContext newGame = new GameContext(player1, player2, logic);
		logic.setContext(newGame);
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
