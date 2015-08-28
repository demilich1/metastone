package net.demilich.metastone.gui.dialog;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import net.demilich.metastone.gui.IconFactory;

public class UserDialog extends BorderPane implements EventHandler<ActionEvent> {

	@FXML
	private Label headerLabel;

	@FXML
	private Label textLabel;

	@FXML
	private ImageView icon;

	@FXML
	private Button positiveButton;

	@FXML
	private Button negativeButton;

	private IDialogListener dialogHandler;

	public UserDialog(String title, String message, DialogType dialogType) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/UserDialog.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		icon.setImage(IconFactory.getDialogIcon(dialogType));
		headerLabel.setText(title);
		textLabel.setText(message);

		positiveButton.setOnAction(this);
		negativeButton.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == positiveButton) {
			setDialogResult(DialogResult.OK);
		} else if (event.getSource() == negativeButton) {
			setDialogResult(DialogResult.CANCEL);
		}
	}

	public void setDialogHandler(IDialogListener dialogHandler) {
		this.dialogHandler = dialogHandler;
	}

	private void setDialogResult(DialogResult result) {
		if (dialogHandler != null) {
			dialogHandler.onDialogClosed(result);
		}
		this.getScene().getWindow().hide();
	}

}
