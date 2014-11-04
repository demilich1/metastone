package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.importer.HearthPwnImporter;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.importer.IDeckImporter;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.DialogNotification;
import net.pferdimanzug.hearthstone.analyzer.gui.dialog.DialogType;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class ImportDeckCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		String url = (String) notification.getBody();

		// TODO: add logic to determine which importer to use
		IDeckImporter importer = new HearthPwnImporter();
		Deck importedDeck = importer.importFrom(url);
		if (importedDeck == null) {
			DialogNotification dialogNotification = new DialogNotification(
					"Error",
					"Import of deck failed. Please make sure to provide a valid URL. At the moment, only hearthpwn.com is supported for deck import.",
					DialogType.ERROR);
			notifyObservers(dialogNotification);
			return;
		}
		getFacade().sendNotification(GameNotification.SET_ACTIVE_DECK, importedDeck);
		getFacade().sendNotification(GameNotification.SAVE_ACTIVE_DECK);
	}

}
