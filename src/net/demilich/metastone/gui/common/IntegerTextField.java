package net.demilich.metastone.gui.common;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class IntegerTextField extends RestrictedTextField {

	private final IntegerProperty valueProperty = new SimpleIntegerProperty();

	public IntegerTextField(int maxLength) {
		setRestrict("\\d*");
		setMaxLength(maxLength);
	}

	public int getIntValue() {
		return valueProperty().get();
	}

	public void setIntValue(int value) {
		setText(String.valueOf(value));
	}

	@Override
	protected void validInput(String validInput) {
		valueProperty().set(validInput.length() > 0 ? Integer.parseInt(validInput) : 0);
		if (validInput.length() == 0) {
			setIntValue(0);
		}
	}

	public IntegerProperty valueProperty() {
		return valueProperty;
	}

}
