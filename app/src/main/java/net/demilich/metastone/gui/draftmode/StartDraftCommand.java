package net.demilich.metastone.gui.draftmode;

import com.hiddenswitch.proto3.draft.DraftContext;
import com.hiddenswitch.proto3.draft.HumanDraftBehaviour;
import com.hiddenswitch.proto3.net.client.RemoteGameContext;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.behaviour.human.HumanBehaviour;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.game.gameconfig.PlayerConfig;
import net.demilich.metastone.game.logic.GameLogic;
import net.demilich.metastone.game.visuals.GameContextVisualizable;
import net.demilich.metastone.gui.playmode.config.MatchmakingTask;
import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;

public class StartDraftCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		StartDraftOptions options = (StartDraftOptions)notification.getBody();
		String sessionId = options.sessionId;

		HumanDraftBehaviour behaviour = new HumanDraftBehaviour();
		DraftContext context = new DraftContext();
		context.setBehaviour(behaviour);

		context.accept(done -> {
			final Deck deck = context.getPublicState().createDeck();

			// Start a game with the resulting deck
			GameConfig gameConfig = new GameConfig();

			PlayerConfig playerConfig = new PlayerConfig(deck, new HumanBehaviour());
			gameConfig.setPlayerConfig1(playerConfig);

			Alert dialog = new Alert(Alert.AlertType.INFORMATION,
					"Finding another player...",
					ButtonType.CANCEL);

			dialog.setTitle("Matchmaking");
			dialog.setHeaderText(null);


			MatchmakingTask matchmaking = new MatchmakingTask(sessionId, deck);

			matchmaking.setOnSucceeded(e -> {
				dialog.close();
			});

			matchmaking.setOnFailed(e -> {
				dialog.close();
			});

			new Thread(matchmaking).start();
			dialog.showAndWait();
			matchmaking.stop();

			ClientConnectionConfiguration clientConnectionConfiguration = matchmaking.getConnection();

			if (clientConnectionConfiguration != null) {
				// TODO: The matchmaker should really do its best to return the correct public URL
				gameConfig.setConnection(new ClientConnectionConfiguration(
						BuildConfig.GAMESESSIONS_HOST,
						clientConnectionConfiguration.getPort(),
						clientConnectionConfiguration.getFirstMessage()
				));
			} else {
				// Do nothing and cancel.
				return;
			}

			gameConfig.setMultiplayer(true);

			NotificationProxy.sendNotification(GameNotification.COMMIT_PLAYMODE_CONFIG, gameConfig);
		});
	}

}
