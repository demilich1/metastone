package com.hiddenswitch.proto3;

import java.io.IOException;
import java.net.URISyntaxException;

import com.hiddenswitch.proto3.server.SocketServerSession;

import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;

public class MetaStoneSimpleServer {

	public static void main(String[] args) {
		SocketServerSession ssc = new SocketServerSession();
		try {
			CardCatalogue.copyCardsFromResources();
			CardCatalogue.loadCardsFromFilesystem();
		} catch (IOException | URISyntaxException | CardParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(ssc).start();
		System.out.println("working");
	}

}
