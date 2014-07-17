package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.importer.HearthPwnImporter;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.importer.IDeckImporter;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class ImportDeckCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		String url = (String) notification.getBody();

		// TODO: add intelligence to determine which importer to use
		IDeckImporter importer = new HearthPwnImporter();
		Deck importedDeck = importer.importFrom(url);
		if (importedDeck == null) {
			System.out.println("ERROR importing deck");
			return;
		}
		getFacade().sendNotification(GameNotification.SET_ACTIVE_DECK, importedDeck);
		getFacade().sendNotification(GameNotification.SAVE_ACTIVE_DECK);
	}

}
