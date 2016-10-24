package com.hiddenswitch.proto3.net.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.hiddenswitch.proto3.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.behaviour.NullBehaviour;
import com.hiddenswitch.proto3.server.PregamePlayerConfiguration;
import net.demilich.metastone.game.behaviour.human.HumanBehaviour;
import net.demilich.metastone.game.cards.NullHeroCard;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

@DynamoDBDocument
public class GamePlayer {
	private String userId;
	private PlayerProfile profile;
	private ChannelType channelType = ChannelType.SQS;
	private Deck deck;

	public GamePlayer() {
	}

	@DynamoDBIgnore
	public PlayerConfig getPlayerConfig() {
		PlayerConfig playerConfig = new PlayerConfig();
		playerConfig.setDeck(getDeck());
		// TODO: Use actual hero cards
		NullHeroCard nullHeroCard = new NullHeroCard(getDeck().getHeroClass());
		playerConfig.setHeroCard(nullHeroCard);
		playerConfig.setHideCards(true);
		playerConfig.setName(getProfile().name);
		playerConfig.setBehaviour(new NullBehaviour());
		return playerConfig;
	}

	@DynamoDBIgnore
	public PregamePlayerConfiguration getPregamePlayerConfig() {
		PregamePlayerConfiguration config = new PregamePlayerConfiguration(getDeck(), getProfile().name);
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

	@DynamoDBTypeConvertedEnum
	public ChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelType channelType) {
		this.channelType = channelType;
	}

	@DynamoDBTypeConverted(converter = DeckConverter.class)
	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}
}

