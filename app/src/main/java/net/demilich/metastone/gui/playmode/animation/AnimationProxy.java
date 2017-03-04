package net.demilich.metastone.gui.playmode.animation;

import net.demilich.nittygrittymvc.Proxy;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.visuals.GameContextVisuals;

public class AnimationProxy extends Proxy<GameNotification> {

	public static final String NAME = "AnimationProxy";

	private GameContextVisuals context;
	private int animationsRunning;

	public AnimationProxy() {
		super(NAME);
	}

	public void animationCompleted() {
		if (--animationsRunning == 0) {
			context.setBlockedByAnimation(false);
		}
	}

	public void animationStarted() {
		animationsRunning++;
	}

	public GameContextVisuals getContext() {
		return context;
	}

	public void setContext(GameContextVisuals context) {
		this.context = context;
	}

}
