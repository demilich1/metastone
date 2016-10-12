package com.hiddenswitch.proto3.net.models;

import net.demilich.metastone.game.cards.NullHeroCard;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.gameconfig.PlayerConfig;

public class GamePlayer {
	public String userId;
	public PlayerProfile profile;
	public String channelId;
	public ChannelType channelType = ChannelType.SQS;
	public Deck deck;

	public PlayerConfig getPlayerConfig() {
		PlayerConfig playerConfig = new PlayerConfig();
		playerConfig.setDeck(deck);
		// TODO: Use actual hero cards
		NullHeroCard nullHeroCard = new NullHeroCard(deck.getHeroClass());
		playerConfig.setHeroCard(nullHeroCard);
		playerConfig.setHideCards(true);
		playerConfig.setName(profile.name);
		return playerConfig;
	}
}

