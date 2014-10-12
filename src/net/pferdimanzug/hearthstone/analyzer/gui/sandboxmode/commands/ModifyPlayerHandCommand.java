package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.SandboxProxy;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class ModifyPlayerHandCommand extends SimpleCommand<GameNotification>{

	@Override
	public void execute(INotification<GameNotification> notification) {
		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);
		
		Player player = sandboxProxy.getSelectedPlayer();
		CardCollection modifiedHand = (CardCollection) notification.getBody();
		GameContext context = sandboxProxy.getSandbox();
		
		player.getHand().removeAll();
		for (Card card : modifiedHand) {
			context.getLogic().receiveCard(player.getId(), card);
		}
		
		sendNotification(GameNotification.UPDATE_SANDBOX_STATE, sandboxProxy.getSandbox());
	}

}
