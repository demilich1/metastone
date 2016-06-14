package net.demilich.metastone.game.cards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Predicate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.demilich.metastone.BuildConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.utils.ResourceLoader;
import net.demilich.metastone.utils.ResourceInputStream;

public class CardCatalogue {

	private final static CardCollection cards = new CardCollection();
	public static final String CARDS_FOLDER = "cards";
	private static final String CARDS_COPIED_PROPERTY = "cards.copied";
	private static Logger logger = LoggerFactory.getLogger(CardCatalogue.class);

	public static void add(Card card) {
		cards.add(card);
	}

	public static CardCollection getAll() {
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			result.add(card);
		}
		return result;
	}

	public static Card getCardById(String id) {
		for (Card card : cards) {
			if (card.getCardId() != null && card.getCardId().equalsIgnoreCase(id)) {
				return card.clone();
			}
		}

		return null;
	}

	public static Card getCardByName(String name) {
		for (Card card : cards) {
			if (card.isCollectible() && card.getName().equals(name)) {
				return card.clone();
			}
		}

		return null;
	}

	public static CardCollection getHeroes() {
		return query(card -> card.isCollectible() && card.getCardType() == CardType.HERO);
	}
	
	public static CardCollection getHeroPowers() {
		return query(card -> card.isCollectible() && card.getCardType() == CardType.HERO_POWER);
	}
	
	public static CardCollection query(DeckFormat deckFormat) {
		return query(deckFormat, (CardType) null, (Rarity) null, (HeroClass) null, (Attribute) null);
	}

	public static CardCollection query(DeckFormat deckFormat, CardType cardType) {
		return query(deckFormat, cardType, (Rarity) null, (HeroClass) null, (Attribute) null);
	}

	public static CardCollection query(DeckFormat deckFormat, HeroClass heroClass) {
		return query(deckFormat, (CardType) null, (Rarity) null, heroClass, (Attribute) null);
	}

	public static CardCollection query(DeckFormat deckFormat, CardType cardType, Rarity rarity, HeroClass heroClass) {
		return query(deckFormat, cardType, rarity, heroClass, (Attribute) null);
	}

	public static CardCollection query(DeckFormat deckFormat, CardType cardType, Rarity rarity, HeroClass heroClass, Attribute tag) {
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (!deckFormat.inSet(card)) {
				continue;
			}
			if (!card.isCollectible()) {
				continue;
			}
			if (cardType != null && !card.getCardType().isCardType(cardType)) {
				continue;
			}
			// per default, do not include heroes or hero powers
			if (card.getCardType().isCardType(CardType.HERO_POWER) || card.getCardType().isCardType(CardType.HERO)) {
				continue;
			}
			if (rarity != null && !card.getRarity().isRarity(rarity)) {
				continue;
			}
			if (heroClass != null && card.getClassRestriction() != heroClass) {
				continue;
			}
			if (tag != null && !card.hasAttribute(tag)) {
				continue;
			}
			result.add(card.clone());
		}

		return result;
	}

	public static CardCollection query(Predicate<Card> filter) {
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (filter.test(card)) {
				result.add(card);
			}
		}
		return result;
	}

	public static void loadCards() throws IOException, URISyntaxException {

		// load cards from ~/metastone/cards on the file system
		Collection<ResourceInputStream> inputStreams = ResourceLoader.loadJsonInputStreams(BuildConfig.USER_HOME_METASTONE + File.separator + CARDS_FOLDER, true);

		Map<String, CardDesc> cardDesc = new HashMap<String, CardDesc>();
		CardParser cardParser = new CardParser();
		for (ResourceInputStream resourceInputStream : inputStreams) {
			try {
				CardDesc desc = cardParser.parseCard(resourceInputStream);
				if (cardDesc.containsKey(desc.id)) {
					logger.error("Card id {} is duplicated!", desc.id);
				}
				cardDesc.put(desc.id, desc);
			} catch (Exception e) {
				logger.error("Trouble reading " + resourceInputStream.fileName);
				throw e;
			}
		}

		for (CardDesc desc : cardDesc.values()) {
			Card instance = desc.createInstance();
			CardCatalogue.add(instance);
			logger.debug("Adding {} to CardCatalogue", instance);
		}
	}

	public static void copyCardsFromJar() throws IOException, URISyntaxException {
		Properties prop = new Properties();
		InputStream input = null;
		FileOutputStream output = null;
		String propertiesFilePath = BuildConfig.USER_HOME_METASTONE + File.separator + "metastone.properties";
		try {
			File propertiesFile = new File(propertiesFilePath);
			if (!propertiesFile.exists()) {
				propertiesFile.createNewFile();
			}

			input = new FileInputStream(propertiesFile);
			// load properties file
			prop.load(input);

			// if we have not copied cards to the USER_HOME_METASTONE cards folder, then do so now
			if (!Boolean.parseBoolean(prop.getProperty(CARDS_COPIED_PROPERTY))) {
				ResourceLoader.copyFromResources(CARDS_FOLDER, BuildConfig.USER_HOME_METASTONE + File.separator + CARDS_FOLDER);

				output = new FileOutputStream(propertiesFile);
				// set a property to indicate that we have copied the cards
				prop.setProperty(CARDS_COPIED_PROPERTY, Boolean.TRUE.toString());
				// write properties file
				prop.store(output, null);
			}

		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
