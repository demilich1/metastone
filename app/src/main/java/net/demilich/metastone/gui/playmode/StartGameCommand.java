package net.demilich.metastone.gui.playmode;

import com.hiddenswitch.proto3.net.client.RemoteGameContext;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.ProceduralPlayer;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.logic.ProceduralGameLogic;
import net.demilich.metastone.game.visuals.GameContextVisualizable;
import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;

public class StartGameCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		GameConfig gameConfig = (GameConfig) notification.getBody();

		PlayerConfig playerConfig1 = gameConfig.getPlayerConfig1();
		PlayerConfig playerConfig2 = gameConfig.getPlayerConfig2();

		Player player1 = null;
		Player player2 = null;

		DeckFormat deckFormat = gameConfig.getDeckFormat();

		GameContext newGame;

		if (gameConfig.isMultiplayer()) {
			player1 = new ProceduralPlayer(playerConfig1);
			player2 = new ProceduralPlayer(playerConfig1);
			newGame = new RemoteGameContext(player1, player2, new ProceduralGameLogic(), deckFormat, gameConfig.getHost(), gameConfig.getPort());
		} else {
			player1 = new Player(playerConfig1);
			player2 = new Player(playerConfig2);
			newGame = new GameContextVisualizable(player1, player2, new GameLogic(), deckFormat);
		}

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				NotificationProxy.sendNotification(GameNotification.PLAY_GAME, newGame);
			}
		});
		t.setDaemon(true);
		t.start();
	}

}
