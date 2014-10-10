package net.pferdimanzug.hearthstone.analyzer.game.behaviour.human;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.IActionSelectionListener;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.Behaviour;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

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
		waitingForInput = true;
		HumanMulliganOptions options = new HumanMulliganOptions(player, this, cards);
		ApplicationFacade.getInstance().sendNotification(GameNotification.HUMAN_PROMPT_FOR_MULLIGAN, options);
		while (waitingForInput) {
			try {
				Thread.sleep(100);
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
		ApplicationFacade.getInstance().sendNotification(GameNotification.HUMAN_PROMPT_FOR_ACTION, options);
		while (waitingForInput) {
			try {
				Thread.sleep(100);
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
