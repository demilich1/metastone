package net.pferdimanzug.hearthstone.analyzer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import com.sun.javafx.perf.PerformanceTracker;

public class HearthstoneAnalyzer extends Application {

	public static void main(String[] args) {
		//DevCheckCardCompleteness.updateCardCatalogue();
		//DevCheckCardCompleteness.writeImplementedCardsToFile("implemented_cards.csv");
		launch(args);
		//DevCheckCardCompleteness.assignUniqueIdToEachCard();
		 //new HearthstoneAnalyzer().launchDebugGame();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("MetaStone");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);

		ApplicationFacade facade = (ApplicationFacade) ApplicationFacade.getInstance();
		final HearthstoneAnalyzer instance = new HearthstoneAnalyzer();
		facade.startUp(instance);

		StackPane root = new StackPane();
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		facade.sendNotification(GameNotification.CANVAS_CREATED, root);
		facade.sendNotification(GameNotification.MAIN_MENU);
		primaryStage.show();
		
		enableFpsLogging(false, scene);
	}
	
	private void enableFpsLogging(boolean enable, Scene scene) {
		if (!enable) {
			return;
		}
		PerformanceTracker tracker = PerformanceTracker.getSceneTracker(scene);
		Timeline timeline = new Timeline(
		   new KeyFrame(Duration.seconds(1), t -> {
		      System.out.println("::FPS = " + tracker.getAverageFPS());
		   }));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

}
