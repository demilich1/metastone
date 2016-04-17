package net.demilich.metastone.gui.playmode;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.AppConfig;
import net.demilich.metastone.ApplicationFacade;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.logic.GameLogic;

public class GameContextVisualizable extends GameContext {

	private final List<GameEvent> gameEvents = new ArrayList<>();

	private boolean blockedByAnimation;

	public GameContextVisualizable(Player player1, Player player2, GameLogic logic, DeckFormat deckFormat) {
		super(player1, player2, logic, deckFormat);
	}

	protected boolean acceptAction(GameAction nextAction) {
		if (!ignoreEvents()) {
			return true;
		}
		while (ignoreEvents()) {
			try {
				Thread.sleep(AppConfig.DEFAULT_SLEEP_DELAY);
			} catch (InterruptedException e) {
			}
		}
		return false;
	}

	@Override
	public void fireGameEvent(GameEvent gameEvent) {
		if (ignoreEvents()) {
			return;
		}
		super.fireGameEvent(gameEvent);
		getGameEvents().add(gameEvent);
	}

	public synchronized List<GameEvent> getGameEvents() {
		return gameEvents;
	}

	public boolean isBlockedByAnimation() {
		return blockedByAnimation;
	}

	@Override
	protected void onGameStateChanged() {
		if (ignoreEvents()) {
			return;
		}

		setBlockedByAnimation(true);
		ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_STATE_UPDATE, this);

		while (blockedByAnimation) {
			try {
				Thread.sleep(AppConfig.DEFAULT_SLEEP_DELAY);
			} catch (InterruptedException e) {
			}
		}
		ApplicationFacade.getInstance().sendNotification(GameNotification.GAME_STATE_LATE_UPDATE, this);
	}

	public void setBlockedByAnimation(boolean blockedByAnimation) {
		this.blockedByAnimation = blockedByAnimation;
	}

}
