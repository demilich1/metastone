package net.demilich.metastone;

import net.demilich.metastone.game.GameContext;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class PlayGameCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		GameContext context = (GameContext) notification.getBody();
		context.play();
		getFacade().sendNotification(GameNotification.GAME_OVER, context);
	}

}
