package net.demilich.metastone.tools;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.gui.common.ComboBoxKeyHandler;

public abstract class CardEditor extends VBox implements ICardEditor {

	public CardEditor(String fxmlFile) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlFile));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@SuppressWarnings("unchecked")
	protected void fillWithSpells(ComboBox<Class<? extends Spell>> comboBox) {
		ObservableList<Class<? extends Spell>> items = FXCollections.observableArrayList();
		String spellPath = "./src/main/java/" + Spell.class.getPackage().getName().replace(".", "/") + "/";
		for (File file : FileUtils.listFiles(new File(spellPath), new String[] { "java" }, false)) {
			String fileName = file.getName().replace(".java", "");
			String spellClassName = Spell.class.getPackage().getName() + "." + fileName;
			Class<? extends Spell> spellClass = null;
			try {
				spellClass = (Class<? extends Spell>) Class.forName(spellClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			items.add(spellClass);
		}
		comboBox.setItems(items);
		comboBox.setOnKeyReleased(new ComboBoxKeyHandler<Class<? extends Spell>>(comboBox));

	}

	@Override
	public Node getPanel() {
		return this;
	}

}
