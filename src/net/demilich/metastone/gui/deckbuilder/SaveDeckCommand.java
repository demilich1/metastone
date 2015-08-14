package net.demilich.metastone.gui.deckbuilder;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;

public class SaveDeckCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);
		String deckName = deckProxy.getActiveDeck().getName().trim();
		if (deckName == null || deckName.equals("")) {
			getFacade().sendNotification(GameNotification.INVALID_DECK_NAME);
			return;
		} else if (!deckProxy.nameAvailable(deckProxy.getActiveDeck())) {
			getFacade().sendNotification(GameNotification.DUPLICATE_DECK_NAME);
			return;
		}
		deckProxy.saveActiveDeck();

		getFacade().removeMediator(DeckBuilderMediator.NAME);
		getFacade().sendNotification(GameNotification.MAIN_MENU);
		getFacade().sendNotification(GameNotification.DECK_BUILDER_SELECTED);
	}

}
