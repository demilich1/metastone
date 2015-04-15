package net.demilich.metastone.tools;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.game.cards.desc.MinionCardDesc;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

class MinionCardPanel extends CardEditor {

	@FXML
	private ComboBox<Race> raceBox;

	@FXML
	private TextField attackField;

	@FXML
	private TextField hpField;

	@FXML
	private ComboBox<Class<? extends Spell>> battlecrySpellBox;
	@FXML
	private ComboBox<TargetSelection> battlecryTargetSelectionBox;

	@FXML
	private ComboBox<Class<? extends Spell>> deathrattleSpellBox;

	private final MinionCardDesc card = new MinionCardDesc();

	public MinionCardPanel() {
		super("MinionCardPanel.fxml");

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
		card.name = "";
		return card;
	}

	

}
