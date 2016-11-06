package com.hiddenswitch.proto3.net.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.hiddenswitch.proto3.net.util.Serialization;
import net.demilich.metastone.game.decks.Bench;
import net.demilich.metastone.game.decks.Deck;

public class DeckConverter implements DynamoDBTypeConverter<String, Deck> {
	@Override
	public String convert(Deck object) {
		return Serialization.serialize(object);
	}

	@Override
	public Deck unconvert(String object) {
		return Serialization.deserialize(object, Deck.class);
	}
}