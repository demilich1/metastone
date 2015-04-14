package net.demilich.metastone.tools;

import java.io.File;
import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.game.cards.desc.MinionCardDesc;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

import org.apache.commons.io.FileUtils;

class MinionCardPanel extends VBox implements ICardEditor {

	@FXML
	private ChoiceBox<Race> raceBox;

	@FXML
	private TextField attackField;

	@FXML
	private TextField hpField;

	@FXML
	private ChoiceBox<Class<? extends Spell>> battlecrySpellBox;
	@FXML
	private ChoiceBox<TargetSelection> battlecryTargetSelectionBox;

	@FXML
	private ChoiceBox<Class<? extends Spell>> deathrattleSpellBox;

	private final MinionCardDesc card = new MinionCardDesc();

	public MinionCardPanel() {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MinionCardPanel.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		raceBox.setItems(FXCollections.observableArrayList(Race.values()));
		raceBox.valueProperty().addListener(this::onRaceChanged);

		battlecryTargetSelectionBox.setItems(FXCollections.observableArrayList(TargetSelection.values()));
		battlecryTargetSelectionBox.getSelectionModel().selectFirst();
		battlecryTargetSelectionBox.valueProperty().addListener(this::onTargetSelectionChanged);

		battlecrySpellBox.setConverter(new SpellStringConverter());
		fillWithSpells(battlecrySpellBox);
		battlecrySpellBox.valueProperty().addListener(this::onBattlecryChanged);

		deathrattleSpellBox.setConverter(new SpellStringConverter());
		fillWithSpells(deathrattleSpellBox);
		deathrattleSpellBox.valueProperty().addListener(this::onDeathrattleChanged);

		attackField.textProperty().addListener(new IntegerListener(value -> card.baseAttack = value));
		hpField.textProperty().addListener(new IntegerListener(value -> card.baseHp = value));
	}

	@SuppressWarnings("unchecked")
	private void fillWithSpells(ChoiceBox<Class<? extends Spell>> choiceBox) {
		ObservableList<Class<? extends Spell>> items = FXCollections.observableArrayList();
		String spellPath = "./src/" + Spell.class.getPackage().getName().replace(".", "/") + "/";
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
		choiceBox.setItems(items);

	}

	private void onTargetSelectionChanged(ObservableValue<? extends TargetSelection> ov, TargetSelection oldValue, TargetSelection newValue) {
		if (card.battlecry == null) {
			card.battlecry = new BattlecryDesc();
		}
		card.battlecry.targetSelection = newValue;
	}

	private void onBattlecryChanged(ObservableValue<? extends Class<? extends Spell>> ov, Class<? extends Spell> oldSpell,
			Class<? extends Spell> newSpell) {
		SpellDesc spell = new SpellDesc(SpellDesc.build(newSpell));
		if (card.battlecry == null) {
			card.battlecry = new BattlecryDesc();
		}
		card.battlecry.spell = spell;
	}

	private void onDeathrattleChanged(ObservableValue<? extends Class<? extends Spell>> ov, Class<? extends Spell> oldSpell,
			Class<? extends Spell> newSpell) {
		card.deathrattle = new SpellDesc(SpellDesc.build(newSpell));
	}

	private void onRaceChanged(ObservableValue<? extends Race> ov, Race oldRace, Race newRace) {
		card.race = newRace;
	}

	@Override
	public CardDesc getCardDesc() {
		card.type = CardType.MINION;
		card.name = "New Minion";
		return card;
	}

	@Override
	public Node getPanel() {
		return this;
	}

}
