package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import de.pferdimanzug.nittygrittymvc.Notification;

public class ModifyPlayerHandNotification extends Notification<GameNotification>{

	private final Player player;
	private final CardCollection modifiedHand;

	public ModifyPlayerHandNotification(Player player, CardCollection modifiedHand) {
		super(GameNotification.MODIFY_PLAYER_HAND);
		this.player = player;
		this.modifiedHand = modifiedHand;
	}

	public Player getPlayer() {
		return player;
	}

	public CardCollection getModifiedHand() {
		return modifiedHand;
	}
}
