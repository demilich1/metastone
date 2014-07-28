package net.pferdimanzug.hearthstone.analyzer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.MinMaxBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.NoAggressionBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid.Innervate;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Ysera;
import net.pferdimanzug.hearthstone.analyzer.game.decks.DeckFactory;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroFactory;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameContextVisualizable;

public class HearthstoneAnalyzer extends Application {

	public static void main(String[] args) {
		//DevCheckCardCompleteness.updateCardCatalogue();
		//DevCheckCardCompleteness.writeImplementedCardsToFile("implemented_cards.csv");
		launch(args);
		//DevCheckCardCompleteness.assignUniqueIdToEachCard();
		 //new HearthstoneAnalyzer().launchDebugGame();
	}

	private void launchDebugGame() {
		ApplicationFacade facade = (ApplicationFacade) ApplicationFacade.getInstance();
		final HearthstoneAnalyzer instance = new HearthstoneAnalyzer();
		facade.startUp(instance);

		for (HeroClass heroClass1 : HeroClass.values()) {
			ApplicationFacade.getInstance().sendNotification(GameNotification.BATCH_START);
			for (HeroClass heroClass2 : HeroClass.values()) {
				if (heroClass1 == HeroClass.ANY || heroClass2 == HeroClass.ANY) {
					continue;
				}

				for (int i = 0; i < 100; i++) {
					Hero hero1 = HeroFactory.createHero(heroClass1);
					Player player1 = new Player("Human", hero1, DeckFactory.getRandomDeck(heroClass1));
					player1.setBehaviour(new MinMaxBehaviour());
					Hero hero2 = HeroFactory.createHero(heroClass2);
					Player player2 = new Player("Bot", hero2, DeckFactory.getRandomDeck(hero2.getHeroClass()));
					player2.setBehaviour(new MinMaxBehaviour());
					GameContext newGame = new GameContext(player1, player2, new GameLogic());
					ApplicationFacade.getInstance().sendNotification(GameNotification.PLAY_GAME, newGame);
				}

			}

			ApplicationFacade.getInstance().sendNotification(GameNotification.BATCH_STOP);
		}
		System.out.println();
		System.out.println("All games have been completed!");
		System.exit(0);
	}

	private void launchHumanDebugGame() {
		HeroClass humanHeroClass = HeroClass.WARLOCK;
		HeroClass aiHeroClass = HeroClass.MAGE;
		Hero hero1 = HeroFactory.createHero(humanHeroClass);
		// Player player1 = new Player("Human", hero1,
		// DebugDecks.getRandomDeck(hero1.getHeroClass()));
		Player player1 = new Player("Human", hero1, DeckFactory.getDeckConsistingof(30, new Ysera(), new Innervate()));
		player1.setBehaviour(new HumanBehaviour());

		Hero hero2 = HeroFactory.createHero(aiHeroClass);
		// Player player2 = new Player("Bot", hero2,
		// DebugDecks.getRandomDeck(hero2.getHeroClass()));
		//Player player2 = new Player("Bot", hero2, DebugDecks.getDeckConsistingof(30, new HarvestGolem(), new IceBlock(), new IceBarrier()));
		Player player2 = new Player("Bot", hero2, DeckFactory.getRandomDeck(aiHeroClass));
		player2.setBehaviour(new NoAggressionBehaviour());
		GameContext newGame = new GameContextVisualizable(player1, player2, new GameLogic());
		ApplicationFacade.getInstance().sendNotification(GameNotification.PLAY_GAME, newGame);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Professor Hearthstone");

		ApplicationFacade facade = (ApplicationFacade) ApplicationFacade.getInstance();
		final HearthstoneAnalyzer instance = new HearthstoneAnalyzer();
		facade.startUp(instance);

		Pane canvas = new Pane();
		Scene scene = new Scene(canvas);
		primaryStage.setScene(scene);
		facade.sendNotification(GameNotification.CANVAS_CREATED, canvas);
		facade.sendNotification(GameNotification.MAIN_MENU);
		primaryStage.show();
	}

}
