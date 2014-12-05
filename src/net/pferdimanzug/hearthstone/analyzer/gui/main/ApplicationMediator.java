package net.pferdimanzug.hearthstone.analyzer.gui.main;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import net.pferdimanzug.hearthstone.analyzer.AppConfig;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.gui.battleofdecks.BattleOfDecksMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.DeckBuilderMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.mainmenu.MainMenuMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.PlayModeMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.config.PlayModeConfigMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.SandboxModeMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.simulationmode.SimulationMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.trainingmode.TrainingModeMediator;

import com.sun.javafx.perf.PerformanceTracker;

import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class ApplicationMediator extends Mediator<GameNotification> {

	public static final String NAME = "ApplicationMediator";

	private Pane root;
	private Label fpsView;

	public ApplicationMediator() {
		super(NAME);
	}

	@SuppressWarnings("restriction")
	private void enableFpsLogging(boolean enable) {
		if (!enable) {
			return;
		}
		fpsView = getFpsDisplay();
		fpsView.setTranslateX(440);
		fpsView.setTranslateY(360);
		PerformanceTracker tracker = PerformanceTracker.getSceneTracker(root.getScene());
		Timeline timeline = new Timeline(
		   new KeyFrame(Duration.seconds(1), t -> {
		      fpsView.setText("Fps: " + String.format("%.2f", tracker.getAverageFPS()));
		   }));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	private Label getFpsDisplay() {
		Label fpsLabel = new Label();
		fpsLabel.setStyle("-fx-font-size: 18pt; -fx-font-family: \"System\";-fx-font-weight: bolder;-fx-text-fill: black;");
		fpsLabel.setAlignment(Pos.BOTTOM_LEFT);
		fpsLabel.setTextAlignment(TextAlignment.LEFT);
		return fpsLabel;
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case CANVAS_CREATED:
			root = (Pane) notification.getBody();
			enableFpsLogging(AppConfig.SHOW_FPS);
			break;
		case SHOW_VIEW:
			final Node view = (Node) notification.getBody();
			root.getChildren().clear();
			root.getChildren().add(view);
			if (AppConfig.SHOW_FPS) {
				root.getChildren().add(fpsView);
			}
			break;
		case MAIN_MENU:
			removeOtherViews();
			getFacade().registerMediator(new MainMenuMediator());
			break;
		default:
			break;
		}
	}
	
	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.CANVAS_CREATED);
		notificationInterests.add(GameNotification.SHOW_VIEW);
		notificationInterests.add(GameNotification.MAIN_MENU);
		return notificationInterests;
	}
	
	private void removeOtherViews() {
		getFacade().removeMediator(PlayModeMediator.NAME);
		getFacade().removeMediator(PlayModeConfigMediator.NAME);
		getFacade().removeMediator(DeckBuilderMediator.NAME);
		getFacade().removeMediator(SimulationMediator.NAME);
		getFacade().removeMediator(TrainingModeMediator.NAME);
		getFacade().removeMediator(SandboxModeMediator.NAME);
		getFacade().removeMediator(BattleOfDecksMediator.NAME);
	}

}
