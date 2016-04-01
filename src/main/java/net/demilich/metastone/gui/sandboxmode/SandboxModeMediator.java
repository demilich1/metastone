package net.demilich.metastone.gui.sandboxmode;

import java.util.ArrayList;
import java.util.List;

import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.behaviour.human.HumanActionOptions;
import net.demilich.metastone.game.behaviour.human.HumanTargetOptions;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;

public class SandboxModeMediator extends Mediator<GameNotification>implements EventHandler<KeyEvent> {

	public static final String NAME = "SandboxModeMediator";

	private final SandboxModeConfigView configView;
	private final SandboxModeView view;

	public SandboxModeMediator() {
		super(NAME);
		configView = new SandboxModeConfigView();
		view = new SandboxModeView();
	}

	@Override
	public void handle(KeyEvent keyEvent) {
		if (keyEvent.getCode() != KeyCode.ESCAPE) {
			return;
		}

		view.disableTargetSelection();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleNotification(final INotification<GameNotification> notification) {

		switch (notification.getId()) {
		case GAME_STATE_LATE_UPDATE:
		case UPDATE_SANDBOX_STATE:
			GameContext context = (GameContext) notification.getBody();
			Platform.runLater(() -> view.updateSandbox(context));
			break;
		case GAME_STATE_UPDATE:
			GameContext context2 = (GameContext) notification.getBody();
			Platform.runLater(() -> view.showAnimations(context2));
			break;
		case SELECT_TARGET:
			HumanTargetOptions targetOptions = (HumanTargetOptions) notification.getBody();
			Platform.runLater(() -> view.getBoardView().enableTargetSelection(targetOptions));
			break;
		case HUMAN_PROMPT_FOR_ACTION:
			HumanActionOptions actionOptions = (HumanActionOptions) notification.getBody();
			Platform.runLater(() -> view.getActionPromptView().setActions(actionOptions));
			break;
		case HUMAN_PROMPT_FOR_TARGET:
			HumanTargetOptions options = (HumanTargetOptions) notification.getBody();
			Platform.runLater(() -> view.enableTargetSelection(options));
			break;
		case SELECT_PLAYER:
			view.onPlayerSelectionChanged((Player) notification.getBody());
			break;
		case COMMIT_SANDBOXMODE_CONFIG:
			getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
			view.setOnKeyPressed(this);
			getFacade().sendNotification(GameNotification.CREATE_NEW_SANDBOX, notification.getBody());
			break;
		case REPLY_DECKS:
			configView.injectDecks((List<Deck>) notification.getBody());
			break;
		case REPLY_DECK_FORMATS:
			configView.injectDeckFormats((List<DeckFormat>) notification.getBody());
			break;
		default:
			break;
		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.UPDATE_SANDBOX_STATE);
		notificationInterests.add(GameNotification.SELECT_TARGET);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_ACTION);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_TARGET);
		notificationInterests.add(GameNotification.GAME_STATE_UPDATE);
		notificationInterests.add(GameNotification.GAME_STATE_LATE_UPDATE);
		notificationInterests.add(GameNotification.SELECT_PLAYER);
		notificationInterests.add(GameNotification.COMMIT_SANDBOXMODE_CONFIG);
		notificationInterests.add(GameNotification.REPLY_DECK_FORMATS);
		notificationInterests.add(GameNotification.REPLY_DECKS);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, configView);
		getFacade().sendNotification(GameNotification.REQUEST_DECKS);
		getFacade().sendNotification(GameNotification.REQUEST_DECK_FORMATS);
	}

}
