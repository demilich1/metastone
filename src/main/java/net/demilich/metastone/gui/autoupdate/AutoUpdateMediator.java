package net.demilich.metastone.gui.autoupdate;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.Notifications;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.gui.IconFactory;
import net.demilich.metastone.gui.dialog.DialogType;
import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;

public class AutoUpdateMediator extends Mediator<GameNotification> {
	
	public static final String NAME = "AutoUpdateMediator";
	
	private Node view;

	public AutoUpdateMediator() {
		super(NAME);
	}
	
	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case CANVAS_CREATED:
			view = (Node) notification.getBody();
			break;
		case NEW_VERSION_AVAILABLE:
			VersionInfo versionInfo = (VersionInfo) notification.getBody();
			Platform.runLater(() -> showUpdateNotification(versionInfo));
			break;
		default:
			break;
		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.CANVAS_CREATED);
		notificationInterests.add(GameNotification.NEW_VERSION_AVAILABLE);
		return notificationInterests;
	}
	
	private void showUpdateNotification(VersionInfo versionInfo) {
		ImageView icon = new ImageView(IconFactory.getDialogIcon(DialogType.INFO));
		icon.setFitWidth(64);
		icon.setFitHeight(64);
		Notifications.create()
        .title("New version available")
        .text("MetaStone '" + versionInfo.version + "' is ready for download")
        .graphic(icon)
        .position(Pos.BOTTOM_CENTER)
        .hideAfter(Duration.seconds(5))
        .owner(view)
        .darkStyle()
        .onAction(this::onNotificationClicked)
        .show();
	}
	
	private void onNotificationClicked(ActionEvent event) {
		try {
			Desktop.getDesktop().browse(new URI("http://www.demilich.net/metastone/download.html"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

}
