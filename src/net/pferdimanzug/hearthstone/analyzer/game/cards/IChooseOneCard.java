package net.pferdimanzug.hearthstone.analyzer.game.cards;

import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;

public interface IChooseOneCard {
	 PlayCardAction playOption1();
	 PlayCardAction playOption2(); 
}
