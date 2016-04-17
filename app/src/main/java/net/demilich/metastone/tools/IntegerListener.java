package net.demilich.metastone.tools;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class IntegerListener implements ChangeListener<String> {

	private final ITextFieldAction action;

	public IntegerListener(ITextFieldAction action) {
		this.action = action;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldText, String newText) {
		if (newText.matches("\\d*")) {
			int value = Integer.parseInt(newText);
			action.onChanged(value);
		} else {
			TextField textField = (TextField) observable;
			textField.setText(oldText);
		}
	}

}
