package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import de.pferdimanzug.nittygrittymvc.Proxy;

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

	public void setSandbox(GameContext sandbox) {
		this.sandbox = sandbox;
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}

}
