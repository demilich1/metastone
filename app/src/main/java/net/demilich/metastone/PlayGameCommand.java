package net.demilich.metastone;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.game.GameContext;

public class PlayGameCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		GameContext context = (GameContext) notification.getBody();
		context.play();
		getFacade().sendNotification(GameNotification.GAME_OVER, context);
	}

}
