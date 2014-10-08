package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.SandboxProxy;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class ModifyPlayerHandCommand extends SimpleCommand<GameNotification>{

	@Override
	public void execute(INotification<GameNotification> notification) {
		ModifyPlayerHandNotification modifyHandNotification = (ModifyPlayerHandNotification) notification;
		
		Player player = modifyHandNotification.getPlayer();
		CardCollection modifiedHand = modifyHandNotification.getModifiedHand();
		
		player.getHand().removeAll();
		player.getHand().addAll(modifiedHand);
		
		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);
		sendNotification(GameNotification.UPDATE_SANDBOX_STATE, sandboxProxy.getSandbox());
	}

}
