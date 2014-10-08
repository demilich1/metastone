package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode.commands;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import de.pferdimanzug.nittygrittymvc.Notification;

public class ModifyPlayerDeckNotification extends Notification<GameNotification>{

	private final Player player;
	private final CardCollection modifiedDeck;

	public ModifyPlayerDeckNotification(Player player, CardCollection modifiedDeck) {
		super(GameNotification.MODIFY_PLAYER_DECK);
		this.player = player;
		this.modifiedDeck = modifiedDeck;
	}

	public Player getPlayer() {
		return player;
	}

	public CardCollection getModifiedDeck() {
		return modifiedDeck;
	}

}
