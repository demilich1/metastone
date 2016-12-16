package net.demilich.metastone.gui.draftmode;

import com.hiddenswitch.proto3.draft.DraftContext;
import com.hiddenswitch.proto3.draft.PublicDraftState;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.behaviour.human.DraftSelectionOptions;
import net.demilich.metastone.game.behaviour.human.HumanActionOptions;
import net.demilich.metastone.game.behaviour.human.HumanMulliganOptions;
import net.demilich.metastone.game.behaviour.human.HumanTargetOptions;
import net.demilich.metastone.gui.playmode.HumanActionPromptView;
import net.demilich.metastone.gui.playmode.HumanMulliganView;
import net.demilich.metastone.gui.playmode.PlayModeView;
import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class DraftModeMediator extends Mediator<GameNotification> implements EventHandler<KeyEvent> {

	public static final String NAME = "DraftModeMediator";

	private final DraftModeView view;

	public DraftModeMediator() {
		super(NAME);
		view = new DraftModeView();
	}

	@Override
	public void handle(KeyEvent keyEvent) {
		if (keyEvent.getCode() != KeyCode.ESCAPE) {
			return;
		}
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
			case DRAFT_STATE_UPDATE:
				PublicDraftState state = (PublicDraftState) notification.getBody();
				Platform.runLater(() -> view.updateDraftState(state));
				break;
			case HUMAN_PROMPT_FOR_DRAFT:
				DraftSelectionOptions draftOptions = (DraftSelectionOptions) notification.getBody();
				Platform.runLater(() -> new DraftSelectionView(draftOptions));
				break;
			default:
				break;
		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.DRAFT_STATE_UPDATE);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_DRAFT);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
		view.getScene().setOnKeyPressed(this);
	}

}
