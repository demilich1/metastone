package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.cards.Card;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bberman on 12/7/16.
 */
public class MulliganResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	public List<Card> discardedCards;
}
