package net.demilich.metastone.tools;

import java.io.File;
import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.desc.SpellDesc;

import org.testng.reporters.Files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class EditorMainWindow extends BorderPane {

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
	private ChoiceBox<Rarity> rarityBox;
	
	@FXML
	private ChoiceBox<HeroClass> heroClassBox;
	
	@FXML
	private TextField manaCostField;
	
	@FXML
	private CheckBox collectibleBox;
	
	@FXML
	private Pane contentPanel;
	
	@FXML
	private Button saveButton;
	
	private final ToggleGroup cardTypeGroup = new ToggleGroup();
	
	private CardDesc card;

	public EditorMainWindow() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditorMainWindow.fxml"));
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
		
		nameField.textProperty().addListener(this::onNameChanged);
		descriptionField.textProperty().addListener(this::onDescriptionChanged);
		
		rarityBox.setItems(FXCollections.observableArrayList(Rarity.values()));
		heroClassBox.setItems(FXCollections.observableArrayList(HeroClass.values()));
		
		setCardEditor(new MinionCardPanel());
		
		rarityBox.valueProperty().addListener(this::onRarityChanged);
		saveButton.setOnAction(this::onSaveButton);
		heroClassBox.valueProperty().addListener(this::onHeroClassChanged);
		collectibleBox.setOnAction(this::onCollectibleChanged);
		manaCostField.textProperty().addListener(new IntegerListener(value -> card.baseManaCost = value));
	}
	
	private void onCollectibleChanged(ActionEvent event) {
		card.collectible = collectibleBox.isSelected();
	}
	
	private void onNameChanged(ObservableValue<? extends String> ov, String oldValue, String newValue) {
		card.name = newValue;
		card.id = getCardId(card.name, card.type);
		idLabel.setText(card.id);
	}
	
	private void onDescriptionChanged(ObservableValue<? extends String> ov, String oldValue, String newValue) {
		card.description = newValue;
	}
	
	private void onRarityChanged(ObservableValue<? extends Rarity> ov, Rarity oldRarity, Rarity newRarity) {
		card.rarity = newRarity;
	}
	
	private void onHeroClassChanged(ObservableValue<? extends HeroClass> ov, HeroClass oldHeroClass, HeroClass newHeroClass) {
		card.heroClass = newHeroClass;
	}
	
	private void setCardEditor(ICardEditor cardEditor) {
		CardDesc newCard = cardEditor.getCardDesc();
		if (card != null) {
			newCard.name = card.name;
			newCard.description = card.description;
			newCard.rarity = card.rarity;
			newCard.heroClass = card.heroClass;
			newCard.baseManaCost = card.baseManaCost;
		} else {
			newCard.name = "New card";
			newCard.rarity = Rarity.FREE;
			newCard.heroClass = HeroClass.ANY;
			newCard.baseManaCost = 0;
			newCard.collectible = true;
		}
		card = newCard;
		card.id = getCardId(card.name, card.type);
		contentPanel.getChildren().setAll(cardEditor.getPanel());
		
		// update ui
		nameField.setText(card.name);
		idLabel.setText(card.id);
		descriptionField.setText(card.description);
		rarityBox.getSelectionModel().select(card.rarity);
		heroClassBox.getSelectionModel().select(card.heroClass);
		manaCostField.setText(String.valueOf(card.baseManaCost));
		collectibleBox.setSelected(card.collectible);
	}
	
	private void onSaveButton(ActionEvent event) {
		save();
	}
	
	private void save() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save card");
		fileChooser.setInitialDirectory(new File("./cards/"));
		fileChooser.setInitialFileName(card.id + ".json");
		File file = fileChooser.showSaveDialog(getScene().getWindow());
		System.out.println("Saving to: " + file.getName());
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		builder.registerTypeAdapter(SpellDesc.class, new SpellDescSerializer());
		Gson gson = builder.create();
		String json = gson.toJson(card);
		try {
			Files.writeFile(json, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getCardId(String cardName, CardType cardType) {
		String result = "";
		String prefix = "";
		switch (cardType) {
		case HERO_POWER:
			prefix = "hero_power_";
			break;
		case MINION:
			prefix = "minion_";
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
		for (String word : cardName.split(" ")) {
			result += prefix + word.replace("'", "").toLowerCase();
			prefix = "_";
		}
		return result;
	}

}
