package net.demilich.metastone.game.cards;

import java.util.Arrays;
import java.util.List;

public class CardParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public CardParseException(List<String> badCards) {
		super(getMessage(badCards));
	}

	private static String getMessage(List<String> badCards) {
		String message = "The following card files contain errors:\n";
		message += Arrays.toString(badCards.toArray());
		message+="\n\nYou can either fix the errors manually or remove the 'cards.copied' entry from your metastone.properties file to restore all cards.";
		return message;
	}

}
