package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.cards.Card;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bberman on 12/7/16.
 */
public class MulliganRequest implements Serializable {
	public List<Card> cards;
}
