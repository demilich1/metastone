package net.demilich.metastone.gui.common;

import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;

public class ComboBoxKeyHandler<T> implements EventHandler<KeyEvent> {
	
	private static final long WORD_DELAY = 2000;

	private String s;
	private final ComboBox<T> box;
	private long lastKeyPress;

	public ComboBoxKeyHandler(ComboBox<T> box) {
		this.box = box;
		s = "";
	}

	@Override
	public void handle(KeyEvent event) {
		if (System.currentTimeMillis() - WORD_DELAY > lastKeyPress) {
			s = "";
		}
		// handle non alphanumeric keys like backspace, delete etc
		if (event.getCode() == KeyCode.BACK_SPACE && s.length() > 0)
			s = s.substring(0, s.length() - 1);
		else
			s += event.getText();
		
		lastKeyPress = System.currentTimeMillis();

		if (s.length() == 0) {
			select(0);
			return;
		}

		for (T item : box.getItems()) {
			
			String name = box.getConverter().toString(item).toLowerCase();
			if (name.startsWith(s)) {
				select(item);
				return;
			}
		}
		// nothing found, reset search string
		s = "";
	}
	
	private void select(int index) {
		select(box.getItems().get(index));
	}
	
	@SuppressWarnings("rawtypes")
	private void select(T item) {
		box.getSelectionModel().select(item);
		ListView lv = ((ComboBoxListViewSkin) box.getSkin()).getListView();
		lv.scrollTo(lv.getSelectionModel().getSelectedIndex());
	}

}
