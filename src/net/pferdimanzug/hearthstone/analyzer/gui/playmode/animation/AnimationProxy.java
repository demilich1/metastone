package net.pferdimanzug.hearthstone.analyzer.gui.playmode.animation;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.GameContextVisualizable;
import de.pferdimanzug.nittygrittymvc.Proxy;

public class AnimationProxy extends Proxy<GameNotification> {

	public static final String NAME = "AnimationProxy";
	
	private GameContextVisualizable context;
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
	
	public GameContextVisualizable getContext() {
		return context;
	}
	
	public void setContext(GameContextVisualizable context) {
		this.context = context;
	}

}
