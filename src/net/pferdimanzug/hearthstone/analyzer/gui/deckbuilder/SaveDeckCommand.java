package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class SaveDeckCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);
		deckProxy.saveActiveDeck();
		
		getFacade().removeMediator(DeckBuilderMediator.NAME);
		getFacade().sendNotification(GameNotification.DECK_BUILDER_SELECTED);
	}


}
