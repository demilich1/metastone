package com.hiddenswitch.cardsgen.functions;

import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.cards.CardParseException;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Iterator;

public class CardGenerator implements FlatMapFunction<String, Card> {
	public CardGenerator() throws CardParseException, IOException, URISyntaxException {
		try {
			CardCatalogue.loadCards();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (CardParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Iterator<Card> call(String s) throws Exception {
		// TODO: Just return fireball for now
		Card fireball = CardCatalogue.getCardById("spell_fireball");

		if (fireball == null) {
			// Return an empty iterator
			return Arrays.asList(new Card[]{}).iterator();
		}

		return Arrays.asList(fireball).iterator();
	}
}
