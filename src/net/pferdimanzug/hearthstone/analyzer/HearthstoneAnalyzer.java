package net.pferdimanzug.hearthstone.analyzer;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.PlayRandomBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage.Fireball;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest.HolySmite;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Anduin;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;

public class HearthstoneAnalyzer {

	public static void main(String[] args) {
		ApplicationFacade facade = (ApplicationFacade) ApplicationFacade.getInstance();
		HearthstoneAnalyzer instance = new HearthstoneAnalyzer();
		//facade.startUp(instance);
		//instance.launchDebugGame();
		
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
		Player player1 = new Player("Human", new Anduin(), createDebugDeck(HeroClass.PRIEST));
		player1.setBehaviour(new PlayRandomBehaviour(player1));
		Player player2 = new Player("Bot", new Jaina(), createDebugDeck(HeroClass.MAGE));
		player2.setBehaviour(new PlayRandomBehaviour(player2));
		GameLogic logic = new GameLogic();
		GameContext newGame = new GameContext(player1, player2, logic);
		logic.setContext(newGame);
		ApplicationFacade.getInstance().sendNotification(GameNotification.START_GAME, newGame);
	}
	
	private CardCollection<Card> createDebugDeck(HeroClass heroClass) {
		switch (heroClass) {
		case MAGE:
			return debugMageDeck();
		case PRIEST:
			return debugPriestDeck();
		default:
			return null;
		}
	}
	
	private CardCollection<Card> debugMageDeck() {
		CardCollection<Card> deck = new CardCollection<>();
		for (int i = 0; i < 30; i++) {
			deck.add(new Fireball());
		}
		return deck;
	}

	private CardCollection<Card> debugPriestDeck() {
		CardCollection<Card> deck = new CardCollection<>();
		for (int i = 0; i < 30; i++) {
			deck.add(new HolySmite());
		}
		return deck;
	}

}
