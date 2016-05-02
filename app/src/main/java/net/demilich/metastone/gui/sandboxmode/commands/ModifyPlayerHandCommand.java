package net.demilich.metastone.gui.sandboxmode.commands;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.gui.sandboxmode.SandboxProxy;

public class ModifyPlayerHandCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);

		Player player = sandboxProxy.getSelectedPlayer();
		CardCollection modifiedHand = (CardCollection) notification.getBody();
		GameContext context = sandboxProxy.getSandbox();

		for (Card card : player.getHand().toList()) {
			context.getLogic().removeCard(player.getId(), card);
		}
		for (Card card : modifiedHand) {
			context.getLogic().receiveCard(player.getId(), card);
		}

		sendNotification(GameNotification.UPDATE_SANDBOX_STATE, sandboxProxy.getSandbox());
	}

}
