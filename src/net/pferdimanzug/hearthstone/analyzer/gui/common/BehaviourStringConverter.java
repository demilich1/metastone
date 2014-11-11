package net.pferdimanzug.hearthstone.analyzer.gui.common;

import javafx.util.StringConverter;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;

public class BehaviourStringConverter extends StringConverter<IBehaviour> {

	@Override
	public IBehaviour fromString(String string) {
		return null;
	}

	@Override
	public String toString(IBehaviour behaviour) {
		return behaviour.getName();
	}

}