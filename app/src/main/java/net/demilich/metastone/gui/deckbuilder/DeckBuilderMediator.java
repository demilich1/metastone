package net.demilich.metastone.gui.deckbuilder;

import java.util.ArrayList;
import java.util.List;

import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.gui.dialog.DialogNotification;
import net.demilich.metastone.gui.dialog.DialogType;

public class DeckBuilderMediator extends Mediator<GameNotification> {

	public static final String NAME = "DeckBuilderMediator";

	private final DeckBuilderView view;

	public DeckBuilderMediator() {
		super(NAME);
		view = new DeckBuilderView();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case CREATE_NEW_DECK:
			view.createNewDeck();
			break;
		case EDIT_DECK:
			view.editDeck((Deck) notification.getBody());
			break;
		case ACTIVE_DECK_CHANGED:
			view.activeDeckChanged((Deck) notification.getBody());
			break;
		case FILTERED_CARDS:
			view.filteredCards((List<Card>) notification.getBody());
			break;
		case DECKS_LOADED:
			view.displayDecks((List<Deck>) notification.getBody());
			break;
		case INVALID_DECK_NAME:
			DialogNotification dialogNotification = new DialogNotification("Name your deck", "Please enter a valid name for this deck.",
					DialogType.WARNING);
			getFacade().notifyObservers(dialogNotification);
			break;
		case DUPLICATE_DECK_NAME:
			getFacade().notifyObservers(new DialogNotification("Duplicate deck name",
					"This deck name was already used for another deck. Please choose another name", DialogType.WARNING));
			break;
		default:
			break;
		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.CREATE_NEW_DECK);
		notificationInterests.add(GameNotification.EDIT_DECK);
		notificationInterests.add(GameNotification.FILTERED_CARDS);
		notificationInterests.add(GameNotification.ACTIVE_DECK_CHANGED);
		notificationInterests.add(GameNotification.DECKS_LOADED);
		notificationInterests.add(GameNotification.DECK_FORMATS_LOADED);
		notificationInterests.add(GameNotification.INVALID_DECK_NAME);
		notificationInterests.add(GameNotification.DUPLICATE_DECK_NAME);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
		getFacade().sendNotification(GameNotification.LOAD_DECKS);
	}

}
