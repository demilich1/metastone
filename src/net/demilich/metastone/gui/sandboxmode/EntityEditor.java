package net.demilich.metastone.gui.sandboxmode;

import java.util.EnumMap;
import java.util.Map;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.utils.TagValueType;
import net.demilich.metastone.gui.common.IntegerTextField;
import net.demilich.metastone.utils.ICallback;

public class EntityEditor extends SandboxEditor {

	private class PairKeyFactory implements Callback<TableColumn.CellDataFeatures<GameTagEntry, String>, ObservableValue<String>> {
		@Override
		public ObservableValue<String> call(TableColumn.CellDataFeatures<GameTagEntry, String> data) {
			return new ReadOnlyObjectWrapper<>(data.getValue().getName());
		}
	}
	private class PairValueCell extends TableCell<GameTagEntry, Object> {
		@Override
		protected void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);

			if (item == null) {
				return;
			}
			GameTagEntry entry = (GameTagEntry) item;
			TagValueType tagValueType = entry.getValueType();
			GameTag tag = entry.getTag();
			if (tagValueType == TagValueType.INTEGER) {
				IntegerTextField numericTextfield = getNumericTextField();
				numericTextfield.setIntValue(entry.getValueInt());
				numericTextfield.valueProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
						workingCopy.put(tag, numericTextfield.getIntValue());
					}
				});
				setGraphic(numericTextfield);
				setText(null);
			} else if (tagValueType == TagValueType.BOOLEAN) {
				CheckBox checkBox = new CheckBox();
				checkBox.setSelected(entry.getValueBool());
				checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
						if (checkBox.isSelected()) {
							workingCopy.put(tag, 1);
						} else {
							workingCopy.remove(tag);
						}

					}
				});
				setGraphic(checkBox);
				setText(null);
			} else {
				setGraphic(null);
				setText(entry.getValue().toString());
			}

		}
	}

	private class PairValueFactory implements Callback<TableColumn.CellDataFeatures<GameTagEntry, Object>, ObservableValue<Object>> {
		@Override
		public ObservableValue<Object> call(TableColumn.CellDataFeatures<GameTagEntry, Object> data) {
			return new ReadOnlyObjectWrapper<>(data.getValue());
		}
	}
	private final Map<GameTag, Object> workingCopy = new EnumMap<GameTag, Object>(GameTag.class);
	private final Entity entity;

	@FXML
	private TableView<GameTagEntry> propertiesTable;

	@FXML
	private TableColumn<GameTagEntry, String> nameColumn;

	@FXML
	private TableColumn<GameTagEntry, Object> valueColumn;
	
	private final ICallback callback;

	public EntityEditor(Entity entity, ICallback callback) {
		super("EntityEditor.fxml");
		this.entity = entity;
		this.callback = callback;
		setTitle("Edit " + entity.getName());

		okButton.setOnAction(this::handleOkButton);
		cancelButton.setOnAction(this::handleCancelButton);

		nameColumn.setCellValueFactory(new PairKeyFactory());
		valueColumn.setCellValueFactory(new PairValueFactory());

		valueColumn.setCellFactory(new Callback<TableColumn<GameTagEntry, Object>, TableCell<GameTagEntry, Object>>() {
			@Override
			public TableCell<GameTagEntry, Object> call(TableColumn<GameTagEntry, Object> column) {
				return new PairValueCell();
			}
		});

		addTagsIfMissing(entity);
		populateTable(entity);
	}

	private void addTagIfMissing(Entity entity, GameTag tag, Object defaultValue) {
		if (entity.hasTag(tag)) {
			return;
		}
		entity.setTag(tag, defaultValue);
	}

	private void addTagsIfMissing(Entity entity) {
		switch (entity.getEntityType()) {
		case CARD:
			break;
		case HERO:
			addTagIfMissing(entity, GameTag.ARMOR, 0);
			break;
		case MINION:
			addTagIfMissing(entity, GameTag.DIVINE_SHIELD, 0);
			addTagIfMissing(entity, GameTag.WINDFURY, 0);
			addTagIfMissing(entity, GameTag.FROZEN, 0);
			addTagIfMissing(entity, GameTag.TEMPORARY_ATTACK_BONUS, 0);
			addTagIfMissing(entity, GameTag.HP_BONUS, 0);
			addTagIfMissing(entity, GameTag.ATTACK_BONUS, 0);
			addTagIfMissing(entity, GameTag.CHARGE, 0);
			addTagIfMissing(entity, GameTag.STEALTH, 0);
			addTagIfMissing(entity, GameTag.TAUNT, 0);
			break;
		case WEAPON:
			break;
		default:
			break;

		}
	}

	private IntegerTextField getNumericTextField() {
		IntegerTextField textField = new IntegerTextField(3);
		textField.setMaxWidth(100);
		return textField;
	}

	private void handleCancelButton(ActionEvent actionEvent) {
		this.getScene().getWindow().hide();
	}

	private void handleOkButton(ActionEvent actionEvent) {
		entity.getTags().clear();
		for (GameTag tag : workingCopy.keySet()) {
			entity.setTag(tag, workingCopy.get(tag));
		}
		this.getScene().getWindow().hide();
		if (callback != null) {
			callback.call(entity);
		}
	}

	private void populateTable(Entity entity) {
		Map<GameTag, Object> tags = entity.getTags();
		ObservableList<GameTagEntry> data = FXCollections.observableArrayList();

		for (GameTag tag : tags.keySet()) {
			Object value = tags.get(tag);
			workingCopy.put(tag, value);

			GameTagEntry entry = new GameTagEntry(tag, value);
			data.add(entry);

		}
		propertiesTable.getItems().setAll(data);
	}

}
