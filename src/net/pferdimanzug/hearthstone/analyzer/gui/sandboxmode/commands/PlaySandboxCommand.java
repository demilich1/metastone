package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanBehaviour;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.SandboxProxy;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class PlaySandboxCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);
		
		GameContext context = sandboxProxy.getSandbox();
		context.setIgnoreEvents(false);
		
		//TODO: offer choice of behaviour
		for (Player player : context.getPlayers()) {
			player.setBehaviour(new HumanBehaviour());
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				context.playTurn();
			}
		}).start();
		
	}

}
