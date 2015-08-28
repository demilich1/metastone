package net.demilich.metastone.gui.common;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * A text field, which restricts the user's input.
 *
 * @author Christian Schudt
 */
public class RestrictedTextField extends TextField {

	private StringProperty restrict = new SimpleStringProperty();

	private IntegerProperty maxLength = new SimpleIntegerProperty(-1);

	public RestrictedTextField() {

		textProperty().addListener(new ChangeListener<String>() {

			private boolean ignore;

			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
				if (ignore)
					return;
				if (maxLength.get() > -1 && s1.length() > maxLength.get()) {
					ignore = true;
					setText(s1.substring(0, maxLength.get()));
					validInput(getText());
					ignore = false;
					return;
				}

				if (restrict.get() != null && !restrict.get().equals("") && !s1.matches(restrict.get())) {
					ignore = true;
					setText(s);
					ignore = false;
					return;
				}

				validInput(getText());
			}
		});
	}

	public int getMaxLength() {
		return maxLength.get();
	}

	public String getRestrict() {
		return restrict.get();
	}

	public IntegerProperty maxLengthProperty() {
		return maxLength;
	}

	public StringProperty restrictProperty() {
		return restrict;
	}

	/**
	 * Sets the max length of the text field.
	 *
	 * @param maxLength
	 *            The max length.
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength.set(maxLength);
	}

	/**
	 * Sets a regular expression character class which restricts the user input.
	 * <br/>
	 * E.g. [0-9] only allows numeric values.
	 *
	 * @param restrict
	 *            The regular expression.
	 */
	public void setRestrict(String restrict) {
		this.restrict.set(restrict);
	}

	protected void validInput(String validInput) {

	}
}
