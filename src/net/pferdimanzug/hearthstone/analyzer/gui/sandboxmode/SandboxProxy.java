package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import de.pferdimanzug.nittygrittymvc.Proxy;

public class SandboxProxy extends Proxy<GameNotification> {

	public static final String NAME = "SandboxProxy";
	
	private GameContext sandbox;

	public SandboxProxy() {
		super(NAME);
	}

	public GameContext getSandbox() {
		return sandbox;
	}

	public void setSandbox(GameContext sandbox) {
		this.sandbox = sandbox;
	}

}
