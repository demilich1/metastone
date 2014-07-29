package net.pferdimanzug.hearthstone.analyzer.gui.simulationmode;

import net.pferdimanzug.hearthstone.analyzer.gui.playmode.config.PlayerConfigType;

public class PlayerConfigView extends net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.PlayerConfigView {

	public PlayerConfigView() {
		super(PlayerConfigType.SIMULATION);
		
		setPrefHeight(400);
	}

}