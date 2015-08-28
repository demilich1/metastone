package net.demilich.metastone.gui.simulationmode;

import net.demilich.metastone.gui.playmode.config.PlayerConfigType;

public class PlayerConfigView extends net.demilich.metastone.gui.gameconfig.PlayerConfigView {

	public PlayerConfigView() {
		super(PlayerConfigType.SIMULATION);

		setPrefHeight(400);
	}

}