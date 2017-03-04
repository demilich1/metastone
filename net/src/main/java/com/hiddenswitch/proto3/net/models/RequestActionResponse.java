package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.actions.GameAction;

import java.io.Serializable;

/**
 * Created by bberman on 12/7/16.
 */
public class RequestActionResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	public GameAction gameAction;
}
