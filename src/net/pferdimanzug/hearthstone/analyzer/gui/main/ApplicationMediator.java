package net.pferdimanzug.hearthstone.analyzer.gui.main;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.gui.mainmenu.MainMenuMediator;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class ApplicationMediator extends Mediator<GameNotification> {

	public static final String NAME = "ApplicationMediator";

	private Pane root;

	public ApplicationMediator() {
		super(NAME);
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case CANVAS_CREATED:
			root = (Pane) notification.getBody();
			getFacade().registerMediator(new MainMenuMediator());
			break;
		case SHOW_VIEW:
			Node view = (Node) notification.getBody();
			root.getChildren().clear();
			root.getChildren().add(view);
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
		return notificationInterests;
	}

}
