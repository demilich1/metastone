package com.hiddenswitch.proto3.net.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import net.demilich.metastone.game.cards.NullHeroCard;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

@DynamoDBDocument
public class GamePlayer {
	private String userId;
	private PlayerProfile profile;
	private String queueUrl;
	private ChannelType channelType = ChannelType.SQS;
	private Deck deck;

	public GamePlayer() {}

	public PlayerConfig getPlayerConfig() {
		PlayerConfig playerConfig = new PlayerConfig();
		playerConfig.setDeck(getDeck());
		// TODO: Use actual hero cards
		NullHeroCard nullHeroCard = new NullHeroCard(getDeck().getHeroClass());
		playerConfig.setHeroCard(nullHeroCard);
		playerConfig.setHideCards(true);
		playerConfig.setName(getProfile().name);
		return playerConfig;
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

	@DynamoDBAttribute
	public String getQueueUrl() {
		return queueUrl;
	}

	public void setQueueUrl(String queueUrl) {
		this.queueUrl = queueUrl;
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

