package net.demilich.metastone.gui.deckbuilder;

import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;

public class ChangeDeckNameCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		DeckProxy deckProxy = (DeckProxy) getFacade().retrieveProxy(DeckProxy.NAME);

		String newDeckName = (String) notification.getBody();
		deckProxy.getActiveDeck().setName(newDeckName);
	}

}
