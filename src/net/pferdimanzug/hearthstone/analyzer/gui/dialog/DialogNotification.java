package net.pferdimanzug.hearthstone.analyzer.gui.dialog;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import de.pferdimanzug.nittygrittymvc.Notification;

public class DialogNotification extends Notification<GameNotification> {

	private final String title;
	private final String message;
	private final DialogType dialogType;
	private IDialogListener handler;

	public DialogNotification(String title, String message, DialogType dialogType) {
		super(GameNotification.SHOW_USER_DIALOG);
		this.title = title;
		this.message = message;
		this.dialogType = dialogType;
	}

	public DialogType getDialogType() {
		return dialogType;
	}

	public IDialogListener getHandler() {
		return handler;
	}

	public String getMessage() {
		return message;
	}

	public String getTitle() {
		return title;
	}

	public void setHandler(IDialogListener handler) {
		this.handler = handler;
	}

}
