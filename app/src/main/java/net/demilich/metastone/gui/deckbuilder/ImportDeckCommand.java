package net.demilich.metastone.gui.deckbuilder;

import net.demilich.metastone.gui.deckbuilder.importer.ImporterFactory;
import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.gui.deckbuilder.importer.IDeckImporter;
import net.demilich.metastone.gui.dialog.DialogNotification;
import net.demilich.metastone.gui.dialog.DialogType;

public class ImportDeckCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		String url = (String) notification.getBody();

		ImporterFactory factory = new ImporterFactory();
		IDeckImporter importer = factory.createDeckImporter(url);
		Deck importedDeck = null;
		if(importer != null)
			importedDeck = importer.importFrom(url);

		if (importedDeck == null) {
			DialogNotification dialogNotification = new DialogNotification("Error",
					"Import of deck failed. Please make sure to provide a valid URL. At the moment, only hearthpwn.com, tempostorm.com, icy-veins.com, and heartheed.com are supported for deck import.",
					DialogType.ERROR);
			notifyObservers(dialogNotification);
			return;
		}
		getFacade().sendNotification(GameNotification.SET_ACTIVE_DECK, importedDeck);
		getFacade().sendNotification(GameNotification.SAVE_ACTIVE_DECK);
	}

}
