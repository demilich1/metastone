package net.demilich.metastone.tools;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.cards.CardSet;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.game.cards.desc.ParseUtils;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.gui.common.ComboBoxKeyHandler;

class EditorMainWindow extends BorderPane {

	private static String getCardId(CardDesc card) {
		String result = "";
		String prefix = "";
		switch (card.type) {
		case HERO_POWER:
			prefix = "hero_power_";
			break;
		case MINION:
			prefix = card.collectible ? "minion_" : "token_";
			break;
		case SPELL:
		case CHOOSE_ONE:
			prefix = "spell_";
			break;
		case WEAPON:
			prefix = "weapon_";
			break;
		default:
			break;

		}
		for (String word : card.name.split(" ")) {
			String cleansedWord = word.replace("'", "").replace(":", "");
			result += prefix + cleansedWord.toLowerCase();
			prefix = "_";
		}
		return result;
	}

	@FXML
	private RadioButton minionRadioButton;

	@FXML
	private RadioButton spellRadioButton;

	@FXML
	private RadioButton weaponRadioButton;

	@FXML
	private TextField nameField;

	@FXML
	private Label idLabel;

	@FXML
	private TextField descriptionField;

	@FXML
	private ComboBox<Rarity> rarityBox;

	@FXML
	private ComboBox<HeroClass> heroClassBox;

	@FXML
	private ComboBox<CardSet> cardSetBox;

	@FXML
	private TextField manaCostField;

	@FXML
	private CheckBox collectibleBox;

	@FXML
	private Pane contentPanel;

	@FXML
	private Button resetButton;

	@FXML
	private Button saveButton;

	private final ToggleGroup cardTypeGroup = new ToggleGroup();
	private List<ComboBox<Attribute>> attributeBoxes;

	private List<TextField> attributeFields;
	private ICardEditor cardEditor;

	private CardDesc card;

	public EditorMainWindow() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditorMainWindow.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		minionRadioButton.setToggleGroup(cardTypeGroup);
		spellRadioButton.setToggleGroup(cardTypeGroup);
		weaponRadioButton.setToggleGroup(cardTypeGroup);
		minionRadioButton.setSelected(true);

		minionRadioButton.setOnAction(event -> setCardEditor(new MinionCardPanel()));
		spellRadioButton.setOnAction(event -> setCardEditor(new SpellCardPanel()));

		nameField.textProperty().addListener(this::onNameChanged);
		descriptionField.textProperty().addListener(this::onDescriptionChanged);

		rarityBox.setItems(FXCollections.observableArrayList(Rarity.values()));
		heroClassBox.setItems(FXCollections.observableArrayList(HeroClass.values()));
		cardSetBox.setItems(FXCollections.observableArrayList(CardSet.values()));

		setCardEditor(new MinionCardPanel());

		rarityBox.valueProperty().addListener(this::onRarityChanged);
		resetButton.setOnAction(this::reset);
		saveButton.setOnAction(this::onSaveButton);
		heroClassBox.valueProperty().addListener(this::onHeroClassChanged);
		cardSetBox.valueProperty().addListener(this::onCardSetChanged);
		collectibleBox.setOnAction(this::onCollectibleChanged);
		manaCostField.textProperty().addListener(new IntegerListener(value -> card.baseManaCost = value));

		attributeBoxes = new ArrayList<>();
		attributeFields = new ArrayList<>();
		for (int i = 1; i < 99; i++) {
			@SuppressWarnings("unchecked")
			ComboBox<Attribute> box = (ComboBox<Attribute>) lookup("#attributeBox" + i);
			if (box == null) {
				break;
			}
			TextField field = (TextField) lookup("#attributeField" + i);
			attributeBoxes.add(box);
			attributeFields.add(field);
		}
		setupAttributeBoxes();
	}

	private Object getAttributeValue(String valueString) {
		Object value = null;
		if (ParseUtils.tryParseInt(valueString)) {
			value = Integer.parseInt(valueString);
		} else if (ParseUtils.tryParseBool(valueString)) {
			value = Boolean.parseBoolean(valueString);
		} else {
			value = valueString;
		}
		return value;
	}

	private void onAttributesChanged() {
		card.attributes = new EnumMap<Attribute, Object>(Attribute.class);
		for (int i = 0; i < attributeBoxes.size(); i++) {
			ComboBox<Attribute> attributeBox = attributeBoxes.get(i);
			TextField attributeField = attributeFields.get(i);
			if (attributeBox.getSelectionModel().getSelectedItem() == null) {
				continue;
			}

			if (StringUtils.isEmpty(attributeField.getText())) {
				attributeField.setText("true");
			}

			Attribute attribute = attributeBox.getSelectionModel().getSelectedItem();
			Object value = getAttributeValue(attributeField.getText());
			card.attributes.put(attribute, value);
		}
	}

	private void onCardSetChanged(ObservableValue<? extends CardSet> ov, CardSet oldCardSet, CardSet newCardSet) {
		card.set = newCardSet;
	}

	private void onCollectibleChanged(ActionEvent event) {
		card.collectible = collectibleBox.isSelected();
	}

	private void onDescriptionChanged(ObservableValue<? extends String> ov, String oldValue, String newValue) {
		card.description = newValue;
	}

	private void onHeroClassChanged(ObservableValue<? extends HeroClass> ov, HeroClass oldHeroClass, HeroClass newHeroClass) {
		card.heroClass = newHeroClass;
	}

	private void onNameChanged(ObservableValue<? extends String> ov, String oldValue, String newValue) {
		card.name = newValue;
		card.id = getCardId(card);
		idLabel.setText(card.id);
	}

	private void onRarityChanged(ObservableValue<? extends Rarity> ov, Rarity oldRarity, Rarity newRarity) {
		card.rarity = newRarity;
	}

	private void onSaveButton(ActionEvent event) {
		save();
	}

	private void reset(ActionEvent event) {
		for (int i = 0; i < attributeBoxes.size(); i++) {
			attributeBoxes.get(i).valueProperty().set(null);
		}
		card.attributes = null;
		cardEditor.reset();
	}

	private void save() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save card");
		fileChooser.setInitialDirectory(new File("./cards/"));
		fileChooser.setInitialFileName(card.id + ".json");
		File file = fileChooser.showSaveDialog(getScene().getWindow());
		if (file == null) {
			return;
		}
		System.out.println("Saving to: " + file.getName());
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		builder.disableHtmlEscaping();
		builder.registerTypeAdapter(SpellDesc.class, new SpellDescSerializer());
		Gson gson = builder.create();
		String json = gson.toJson(card);
		try {
			// FileUtils.writeStringToFile(file, json);
			Path path = Paths.get(file.getPath());
			Files.write(path, json.getBytes());
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setCardEditor(ICardEditor cardEditor) {
		this.cardEditor = cardEditor;
		CardDesc newCard = cardEditor.getCardDesc();
		if (card != null) {
			newCard.name = card.name;
			newCard.description = card.description;
			newCard.rarity = card.rarity;
			newCard.heroClass = card.heroClass;
			newCard.baseManaCost = card.baseManaCost;
		} else {
			newCard.name = "";
			newCard.rarity = Rarity.FREE;
			newCard.heroClass = HeroClass.ANY;
			newCard.set = CardSet.CUSTOM;
			newCard.baseManaCost = 0;
			newCard.collectible = true;
		}
		card = newCard;
		card.id = getCardId(card);
		contentPanel.getChildren().setAll(cardEditor.getPanel());

		// update ui
		nameField.setText(card.name);
		idLabel.setText(card.id);
		descriptionField.setText(card.description);
		rarityBox.getSelectionModel().select(card.rarity);
		heroClassBox.getSelectionModel().select(card.heroClass);
		cardSetBox.getSelectionModel().select(card.set);
		manaCostField.setText(String.valueOf(card.baseManaCost));
		collectibleBox.setSelected(card.collectible);
	}

	private void setupAttributeBoxes() {
		for (ComboBox<Attribute> comboBox : attributeBoxes) {
			ObservableList<Attribute> items = FXCollections.observableArrayList();
			items.addAll(Attribute.values());
			Collections.sort(items, (obj1, obj2) -> {
				if (obj1 == obj2) {
					return 0;
				}
				if (obj1 == null) {
					return -1;
				}
				if (obj2 == null) {
					return 1;
				}
				return obj1.toString().compareTo(obj2.toString());
			});
			comboBox.setItems(items);
			comboBox.valueProperty().addListener((ov, oldValue, newValue) -> onAttributesChanged());
			comboBox.setOnKeyReleased(new ComboBoxKeyHandler<Attribute>(comboBox));
		}
		for (TextField attributeField : attributeFields) {
			attributeField.textProperty().addListener((ov, oldValue, newValue) -> onAttributesChanged());
		}
	}

}
