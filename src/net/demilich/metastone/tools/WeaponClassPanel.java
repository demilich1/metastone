package net.demilich.metastone.tools;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.game.cards.desc.WeaponCardDesc;

public class WeaponClassPanel extends CardEditor {

	private final WeaponCardDesc card = new WeaponCardDesc();

	public WeaponClassPanel() {
		super("WeaponCardPanel.fxml");
	}

	@Override
	public CardDesc getCardDesc() {
		card.type = CardType.WEAPON;
		card.name = "";
		return card;
	}

	@Override
	public void reset() {
		card.battlecry = null;
		card.deathrattle = null;
	}

}
