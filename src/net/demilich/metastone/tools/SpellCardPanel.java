package net.demilich.metastone.tools;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.game.cards.desc.SpellCardDesc;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SpellCardPanel extends CardEditor {

	@FXML
	private ComboBox<Class<? extends Spell>> spellBox;
	@FXML
	private ComboBox<TargetSelection> targetSelectionBox;

	private SpellCardDesc card = new SpellCardDesc();

	public SpellCardPanel() {
		super("SpellCardPanel.fxml");

		targetSelectionBox.setItems(FXCollections.observableArrayList(TargetSelection.values()));
		targetSelectionBox.getSelectionModel().selectFirst();
		targetSelectionBox.valueProperty().addListener(this::onTargetSelectionChanged);

		spellBox.setConverter(new SpellStringConverter());
		fillWithSpells(spellBox);
		spellBox.valueProperty().addListener(this::onSpellChanged);
	}

	private void onTargetSelectionChanged(ObservableValue<? extends TargetSelection> ov, TargetSelection oldValue, TargetSelection newValue) {
		card.targetSelection = newValue;
	}

	private void onSpellChanged(ObservableValue<? extends Class<? extends Spell>> ov, Class<? extends Spell> oldSpell,
			Class<? extends Spell> newSpell) {
		card.spell = new SpellDesc(SpellDesc.build(newSpell));
	}

	@Override
	public CardDesc getCardDesc() {
		card.type = CardType.SPELL;
		card.targetSelection = TargetSelection.NONE;
		card.name = "";
		return card;
	}

	@Override
	public void reset() {
		spellBox.valueProperty().set(null);
		card.spell = null;
	}
}
