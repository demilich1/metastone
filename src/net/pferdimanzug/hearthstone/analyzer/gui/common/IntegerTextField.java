package net.pferdimanzug.hearthstone.analyzer.gui.common;

public class IntegerTextField extends RestrictedTextField {
	
	public IntegerTextField(int maxLength) {
		setRestrict("[0-9]");
		setMaxLength(maxLength);
	}
	
	public int getIntValue() {
		return Integer.parseInt(getText());
	}
	
	public void setIntValue(int value) {
		setText(String.valueOf(value));
	}

}
