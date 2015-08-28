package net.demilich.metastone.gui.sandboxmode;

import net.demilich.nittygrittymvc.Proxy;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;

public class SandboxProxy extends Proxy<GameNotification> {

	public static final String NAME = "SandboxProxy";

	private GameContext sandbox;
	private Player selectedPlayer;

	public SandboxProxy() {
		super(NAME);
	}

	public GameContext getSandbox() {
		return sandbox;
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSandbox(GameContext sandbox) {
		this.sandbox = sandbox;
	}

	public void setSelectedPlayer(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}

}
