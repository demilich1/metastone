package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.DoNothingBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.logic.GameLogic;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.GameConfig;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.PlayerConfig;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameContextVisualizable;
import net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.SandboxProxy;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class CreateNewSandboxCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				GameConfig gameConfig = (GameConfig) notification.getBody();
				SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);

				PlayerConfig player1Config = gameConfig.getPlayerConfig1();
				Hero hero1 = player1Config.getHero();
				Player player1 = new Player("Player 1", hero1, player1Config.getDeck());
				player1.setBehaviour(new DoNothingBehaviour());

				PlayerConfig player2Config = gameConfig.getPlayerConfig2();
				Hero hero2 = player2Config.getHero();
				Player player2 = new Player("Player 2", hero2, player2Config.getDeck());
				player2.setBehaviour(new DoNothingBehaviour());

				GameContext sandbox = new GameContextVisualizable(player1, player2, new GameLogic());
				sandboxProxy.setSandbox(sandbox);
				sendNotification(GameNotification.UPDATE_SANDBOX_STATE, sandbox);
				
				player1.setBehaviour(player1Config.getBehaviour());
				player2.setBehaviour(player2Config.getBehaviour());
				sandbox.setIgnoreEvents(true);
				sandbox.play();

			}
		});
		thread.setDaemon(true);
		thread.setUncaughtExceptionHandler((t, exception) -> exception.printStackTrace()); 
		thread.start();
	}

}
