package net.demilich.metastone.game.cards;

import net.demilich.metastone.game.actions.PlayCardAction;

public interface IChooseOneCard {
	PlayCardAction[] playOptions();
	PlayCardAction playBothOptions();
	boolean hasBothOptions();
}
