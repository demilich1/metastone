package net.demilich.metastone.gui.cards;

import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.nittygrittymvc.Proxy;

import java.io.IOException;
import java.net.URISyntaxException;

public class CardProxy extends Proxy<GameNotification> {

	public final static String NAME = "CardProxy";

	public CardProxy() {
		super(NAME);
		try {
			CardCatalogue.loadCards();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
