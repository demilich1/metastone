package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.TurnState;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.SandboxProxy;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class StartPlaySandboxCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);
		
		GameContext context = sandboxProxy.getSandbox();
		context.setIgnoreEvents(false);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (context.getTurnState() == TurnState.TURN_ENDED) {
					context.startTurn(context.getActivePlayerId());
				} else {
					context.playTurn();
				}
			}
		}).start();
		
	}

}
