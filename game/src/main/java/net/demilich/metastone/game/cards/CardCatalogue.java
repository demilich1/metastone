package net.demilich.metastone.game.cards;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.utils.MetastoneProperties;
import net.demilich.metastone.utils.ResourceInputStream;
import net.demilich.metastone.utils.ResourceLoader;
import net.demilich.metastone.utils.UserHomeMetastone;

public class CardCatalogue {

	public static final String CARDS_FOLDER = "cards";
	public static final String LOCAL_CARDS_FOLDER = "../cards/src/main/resources/cards/";
	public static final String CARDS_FOLDER_PATH = UserHomeMetastone.getPath() + File.separator + CARDS_FOLDER;
	public static final String CARDS_COPIED_PROPERTY = "cardRevision";

	private static Logger logger = LoggerFactory.getLogger(CardCatalogue.class);

	private final static CardCollection cards = new CardCollection();

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
		return query(null, card -> card.isCollectible() && card.getCardType() == CardType.HERO);
	}

	public static CardCollection getHeroPowers(DeckFormat deckFormat) {
		return query(deckFormat, card -> card.isCollectible() && card.getCardType() == CardType.HERO_POWER);
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

	public static CardCollection query(DeckFormat deckFormat, HeroClass heroClass, HeroClass actualHeroClass) {
		return query(deckFormat, (CardType) null, (Rarity) null, heroClass, (Attribute) null, actualHeroClass);
	}

	public static CardCollection query(DeckFormat deckFormat, CardType cardType, Rarity rarity, HeroClass heroClass, Attribute tag) {
		return query(deckFormat, cardType, rarity, heroClass, tag, null);
	}

	public static CardCollection query(DeckFormat deckFormat, CardType cardType, Rarity rarity, HeroClass heroClass, Attribute tag, HeroClass actualHeroClass) {
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (!deckFormat.isInFormat(card)) {
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
			if (heroClass != null && !card.hasHeroClass(heroClass)) {
				continue;
			}
			if (tag != null && !card.hasAttribute(tag)) {
				continue;
			}
			result.add(card.clone());
		}

		return result;
	}

	public static void loadCardsFromPackage() throws IOException, URISyntaxException, CardParseException {
		synchronized (cards) {
			if (!cards.isEmpty()) {
				return;
			}

			Collection<ResourceInputStream> inputStreams = ResourceLoader.loadJsonInputStreams(CARDS_FOLDER, false);
			loadCards(inputStreams);
		}
	}

	public static CardCollection query(DeckFormat deckFormat, Predicate<Card> filter) {
		CardCollection result = new CardCollection();
		for (Card card : cards) {
			if (deckFormat != null && !deckFormat.isInFormat(card)) {
				continue;
			}
			if (filter.test(card)) {
				result.add(card);
			}
		}
		return result;
	}

    public static void loadCardsFromFilesystem() throws IOException, URISyntaxException, CardParseException {
        // load cards from ~/metastone/cards on the file system
        Collection<ResourceInputStream> inputStreams = ResourceLoader.loadJsonInputStreams(CARDS_FOLDER_PATH, true);
        loadCards(inputStreams);
    }


	private static void loadCards(Collection<ResourceInputStream> inputStreams) throws IOException, URISyntaxException, CardParseException {
		Map<String, CardDesc> cardDesc = new HashMap<String, CardDesc>();
		ArrayList<String> badCards = new ArrayList<>();
		CardParser cardParser = new CardParser();

		for (ResourceInputStream resourceInputStream : inputStreams) {
			try {
				CardDesc desc = cardParser.parseCard(resourceInputStream);
				if (cardDesc.containsKey(desc.id)) {
					logger.error("Card id {} is duplicated!", desc.id);
				}
				cardDesc.put(desc.id, desc);
			} catch (Exception e) {
				//logger.error("Error parsing card '{}'", resourceInputStream.fileName);
				logger.error(e.toString());
				badCards.add(resourceInputStream.fileName);
			}
		}

		for (CardDesc desc : cardDesc.values()) {
			Card instance = desc.createInstance();
			CardCatalogue.add(instance);

		}

		logger.debug("{} cards loaded.", CardCatalogue.cards.getCount());

		if (!badCards.isEmpty()) {
			throw new CardParseException(badCards);
		}
	}

	public static void copyCardsFromResources() throws IOException, URISyntaxException {
		// if we have not copied cards to the USER_HOME_METASTONE cards folder,
		// then do so now
		int cardRevision = MetastoneProperties.getInt(CARDS_COPIED_PROPERTY, 0);
		logger.info("Existing card revision = " + cardRevision);
		if (BuildConfig.CARD_REVISION > cardRevision) {
			logger.info("Card update required: MetaStone card revision is: {}, last card update was with revision {}", BuildConfig.CARD_REVISION, cardRevision);
			ResourceLoader.copyFromResources(CARDS_FOLDER, CARDS_FOLDER_PATH);

			// set a property to indicate that we have copied the cards with current version
			MetastoneProperties.setProperty(CARDS_COPIED_PROPERTY, String.valueOf(BuildConfig.CARD_REVISION));
		} else {
			logger.info("Cards in user home folder are up-to-date: rev {}", cardRevision);
		}
	}
}
