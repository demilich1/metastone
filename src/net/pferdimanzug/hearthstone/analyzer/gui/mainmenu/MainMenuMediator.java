package net.pferdimanzug.hearthstone.analyzer.gui.mainmenu;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.DeckBuilderMediator;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class MainMenuMediator extends Mediator<GameNotification> {
	
	public static final String NAME = "MainMenuMediator";
	
	private final MainMenuView view;

	public MainMenuMediator() {
		super(NAME);
		view = new MainMenuView();
	}
	
	
	
	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
	}



	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case DECK_BUILDER_SELECTED:
			getFacade().registerMediator(new DeckBuilderMediator());
			break;
		}
		
	}
	

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.DECK_BUILDER_SELECTED);
		return notificationInterests;
	}

}
