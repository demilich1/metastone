package net.demilich.metastone.game.behaviour.human;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.IActionSelectionListener;
import net.demilich.metastone.game.behaviour.Behaviour;
import net.demilich.metastone.game.cards.Card;

public class HumanBehaviour extends Behaviour implements IActionSelectionListener {

	private GameAction selectedAction;
	private boolean waitingForInput;
	private List<Card> mulliganCards;

	@Override
	public String getName() {
		return "<Human controlled>";
	}

	@Override
	public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
		if (context.ignoreEvents()) {
			return new ArrayList<Card>();
		}
		waitingForInput = true;
		HumanMulliganOptions options = new HumanMulliganOptions(player, this, cards);
		NotificationProxy.sendNotification(GameNotification.HUMAN_PROMPT_FOR_MULLIGAN, options);
		while (waitingForInput) {
			try {
				Thread.sleep(BuildConfig.DEFAULT_SLEEP_DELAY);
			} catch (InterruptedException e) {
			}
		}
		return mulliganCards;
	}

	@Override
	public void onActionSelected(GameAction action) {
		this.selectedAction = action;
		waitingForInput = false;
	}

	@Override
	public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
		waitingForInput = true;
		HumanActionOptions options = new HumanActionOptions(this, context, player, validActions);
		NotificationProxy.sendNotification(GameNotification.HUMAN_PROMPT_FOR_ACTION, options);
		while (waitingForInput) {
			try {
				Thread.sleep(BuildConfig.DEFAULT_SLEEP_DELAY);
				if (context.ignoreEvents()) {
					return null;
				}
			} catch (InterruptedException e) {
			}
		}
		return selectedAction;
	}

	public void setMulliganCards(List<Card> mulliganCards) {
		this.mulliganCards = mulliganCards;
		waitingForInput = false;
	}

}
