package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class DeckBuilderMediator extends Mediator<GameNotification> {

	public static final String NAME = "DeckBuilderMediator";

	private final DeckBuilderView view;
	private final CardView cardView;
	private final CardListView cardListView;
	private final DeckInfoView deckInfoView;
	private final DeckListView deckListView;

	public DeckBuilderMediator() {
		super(NAME);
		view = new DeckBuilderView();
		cardView = new CardView();
		cardListView = new CardListView();
		deckInfoView = new DeckInfoView();
		deckListView = new DeckListView();
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case CREATE_NEW_DECK:
			view.showMainArea(new ChooseClassView());
			view.showSidebar(null);
			break;
		case EDIT_DECK:
			view.showMainArea(cardView);
			view.showSidebar(cardListView);
			view.showInfoArea(deckInfoView);
			view.showBottomBar(new CardFilterView());
			break;
		case ACTIVE_DECK_CHANGED:
			Deck deck = (Deck) notification.getBody();
			cardListView.displayDeck(deck);
			deckInfoView.updateDeck(deck);
			break;
		case FILTERED_CARDS:
			cardView.displayCards((List<Card>) notification.getBody());
			break;
		case DECKS_LOADED:
			deckListView.displayDecks((List<Deck>) notification.getBody());
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
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
		view.showSidebar(deckListView);
		getFacade().sendNotification(GameNotification.LOAD_DECKS);
	}

}
