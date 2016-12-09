package com.hiddenswitch.proto3;

import java.io.IOException;
import java.net.URISyntaxException;

import com.hiddenswitch.proto3.server.SocketServerImpl;

import io.netty.channel.DefaultChannelId;
import io.vertx.core.Vertx;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import net.demilich.metastone.game.decks.DeckCatalogue;

public class MetaStoneSimpleServer {

	public static void main(String[] args) {
		DefaultChannelId.newInstance();
		Vertx vertx = Vertx.vertx();
		SocketServerImpl ssc = new SocketServerImpl();
		try {
			CardCatalogue.loadCardsFromPackage();
			DeckCatalogue.loadDecksFromPackage();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardParseException e) {
			e.printStackTrace();
		}
		vertx.deployVerticle(ssc);
	}

}
