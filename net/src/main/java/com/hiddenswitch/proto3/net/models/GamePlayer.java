package com.hiddenswitch.proto3.net.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.server.PregamePlayerConfiguration;
import net.demilich.metastone.game.decks.Deck;

@DynamoDBDocument
public class GamePlayer {
	private String userId;
	private PlayerProfile profile;
	private Deck deck;
	private ClientConnectionConfiguration connectionConfiguration;

	public GamePlayer() {
	}

	@DynamoDBIgnore
	public PregamePlayerConfiguration getPregamePlayerConfig() {
		String name = null;
		if (getProfile() != null) {
			name = profile.name;
		}
		PregamePlayerConfiguration config = new PregamePlayerConfiguration(getDeck(), name);
		return config;
	}

	@DynamoDBAttribute
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@DynamoDBAttribute
	public PlayerProfile getProfile() {
		return profile;
	}

	public void setProfile(PlayerProfile profile) {
		this.profile = profile;
	}

	@DynamoDBTypeConverted(converter = DeckConverter.class)
	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	@DynamoDBTypeConverted(converter = ClientConnectionConfigurationConverter.class)
	public ClientConnectionConfiguration getConnectionConfiguration() {
		return connectionConfiguration;
	}

	public void setConnectionConfiguration(ClientConnectionConfiguration connectionConfiguration) {
		this.connectionConfiguration = connectionConfiguration;
	}

}

