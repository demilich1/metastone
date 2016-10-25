package net.demilich.metastone.gui.playmode;

import net.demilich.metastone.NotificationProxy;
import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.MetaStone;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.ProceduralPlayer;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.logic.ProceduralGameLogic;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

public class StartGameCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		GameConfig gameConfig = (GameConfig) notification.getBody();

		PlayerConfig playerConfig1 = gameConfig.getPlayerConfig1();
		PlayerConfig playerConfig2 = gameConfig.getPlayerConfig2();

		Player player1 = new Player(playerConfig1);
		Player player2 = new Player(playerConfig2);
		
		if (MetaStone.procedural){
			player1 = new ProceduralPlayer (playerConfig1);
			player2 = new ProceduralPlayer (playerConfig2);
		}
		
		DeckFormat deckFormat = gameConfig.getDeckFormat();

		GameContext newGame;
		if (MetaStone.procedural){
			newGame = new GameContextVisualizable(player1 , player2, new ProceduralGameLogic(), deckFormat);
		} else {
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
