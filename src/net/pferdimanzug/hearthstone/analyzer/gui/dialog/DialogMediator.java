package net.pferdimanzug.hearthstone.analyzer.gui.dialog;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class DialogMediator extends Mediator<GameNotification> {

	public static final String NAME = "DialogMediator";

	private static Logger logger = LoggerFactory.getLogger(DialogMediator.class);

	private Window root;

	public DialogMediator() {
		super(NAME);
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case CANVAS_CREATED:
			Pane canvas = (Pane) notification.getBody();
			root = canvas.getScene().getWindow();
			break;
		case SHOW_USER_DIALOG:
			showUserDialog((DialogNotification) notification);
			break;
		case SHOW_MODAL_DIALOG:
			showModalDialog((Node) notification.getBody());
			break;
		default:
			logger.warn("Unhandled notification {} in {}", notification, getClass().getSimpleName());
			break;
		}
	}
	
	private void showModalDialog(Node content) {
		new ModalDialog(root, content);
	}

	private void showUserDialog(DialogNotification notification) {
		UserDialog userDialog = new UserDialog(notification.getTitle(), notification.getMessage(), notification.getDialogType());
		userDialog.setDialogHandler(notification.getHandler());
		showModalDialog(userDialog);
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.CANVAS_CREATED);
		notificationInterests.add(GameNotification.SHOW_MODAL_DIALOG);
		notificationInterests.add(GameNotification.SHOW_USER_DIALOG);
		return notificationInterests;
	}

}
