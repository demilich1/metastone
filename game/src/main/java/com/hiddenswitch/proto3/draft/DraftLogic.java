package com.hiddenswitch.proto3.draft;

import net.demilich.metastone.game.cards.*;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * Created by bberman on 12/14/16.
 */
public class DraftLogic {
	public static final float EXPANSION_ODDS_FACTOR = 1.5f;
	public static final float COMMON_ROLL = 0.76f;
	private static final float RARE_ROLL = 0.20f;
	private static final float EPIC_ROLL = 0.03f;
	private final WeakReference<DraftContext> context;
	public static int DRAFTS = 30;
	public static int CARDS_PER_DRAFT = 3;

	public DraftLogic(DraftContext context) {
		this.context = new WeakReference<>(context);

		if (getContext().getPrivateState() == null) {
			getContext().setPrivateState(new PrivateDraftState());
		}
	}

	public void initializeDraft() {
		if (getContext().getPublicState() == null) {
			getContext().setPublicState(new PublicDraftState());
		}

		getContext().getPublicState().heroClassChoices = createHeroChoices();
		getContext().getPublicState().status = DraftStatus.SELECT_HERO;
		notifyPublicStateChanged();
	}

	public void startDraft(HeroClass heroClass) {
		// Determine the cards available to this player for the draft.
		// For now, do not make later parts of the draft dependent on earlier parts.
		getContext().getPublicState().heroClass = heroClass;
		getContext().getPrivateState().cards = createDraftCards(heroClass);
		// Initialize the first card choices
		getContext().getPublicState().currentCardChoices = getContext().getPrivateState().cards.get(0);
		getContext().getPublicState().status = DraftStatus.IN_PROGRESS;
		notifyPublicStateChanged();
	}

	private List<HeroClass> createHeroChoices() {
		List<HeroClass> classes = Arrays.asList(
				HeroClass.DRUID,
				HeroClass.HUNTER,
				HeroClass.MAGE,
				HeroClass.PALADIN,
				HeroClass.PRIEST,
				HeroClass.ROGUE,
				HeroClass.SHAMAN,
				HeroClass.WARLOCK,
				HeroClass.WARRIOR
		);

		Collections.shuffle(classes, getRandom());
		return Arrays.asList(classes.get(0), classes.get(1), classes.get(2));
	}

	private List<List<Card>> createDraftCards(HeroClass hero) {
		ArrayList<List<Card>> draftCards = new ArrayList<>(DRAFTS);

		List<CardSet> equals = Arrays.asList(CardSet.BASIC,
				CardSet.CLASSIC,
				CardSet.BLACKROCK_MOUNTAIN,
				CardSet.GOBLINS_VS_GNOMES,
				CardSet.NAXXRAMAS,
				CardSet.THE_GRAND_TOURNAMENT,
				CardSet.LEAGUE_OF_EXPLORERS,
				CardSet.THE_OLD_GODS,
				CardSet.ONE_NIGHT_IN_KARAZHAN,
				CardSet.MEAN_STREETS_OF_GADGETZHAN);

		// Until we have enough mean streets cards, don't use it
		CardSet latestExpansion = CardSet.MEAN_STREETS_OF_GADGETZHAN;

		Set<CardType> validCardTypes = new HashSet<>(Arrays.asList(
				CardType.CHOOSE_ONE,
				CardType.MINION,
				CardType.SPELL,
				CardType.WEAPON
		));

		for (int draft = 0; draft < DRAFTS; draft++) {
			// Select a rarity at the appropriate frequency
			float rarityRoll = roll();
			Rarity rarity;
			if (rarityRoll < COMMON_ROLL) {
				rarity = Rarity.COMMON;
			} else if (rarityRoll < COMMON_ROLL + RARE_ROLL) {
				rarity = Rarity.RARE;
			} else if (rarityRoll < COMMON_ROLL + RARE_ROLL + EPIC_ROLL) {
				rarity = Rarity.EPIC;
			} else {
				rarity = Rarity.LEGENDARY;
			}

			// Select the card set. The latest expansion gets a 50% bonus
			List<Card> draftChoices = new ArrayList<>(CARDS_PER_DRAFT);

			while (draftChoices.stream().map(Card::getCardId).distinct().count() < 3) {
				float cardSetRoll = roll();
				CardSet set;

				float latestExpansionOdds = EXPANSION_ODDS_FACTOR / (equals.size() + EXPANSION_ODDS_FACTOR);
				if (cardSetRoll < latestExpansionOdds) {
					set = latestExpansion;
				} else {
					float rescaled = (cardSetRoll - latestExpansionOdds) / (1f - latestExpansionOdds);
					int index = (int) (rescaled * equals.size());
					set = equals.get(index);
				}

				// Get neutral and hero cards
				CardCollection classCards = CardCatalogue.query(new DeckFormat().withCardSets(set), c -> {
					return c.hasHeroClass(hero)
							&& c.getRarity() == rarity
							&& validCardTypes.contains(c.getCardType())
							&& c.isCollectible();
				});
				CardCollection neutralCards = CardCatalogue.query(new DeckFormat().withCardSets(set), c -> {
					return c.hasHeroClass(HeroClass.ANY)
							&& c.getRarity() == rarity
							&& validCardTypes.contains(c.getCardType())
							&& c.isCollectible();
				});
				// Add two copies of the class cards and then the neutrals
				CardCollection cards = classCards.clone().addAll(classCards).addAll(neutralCards);

				// Shuffle then choose until we're done
				cards.shuffle(getRandom());

				int card_i = 0;
				while (card_i < CARDS_PER_DRAFT
						&& cards.getCount() > 0
						&& draftChoices.stream().map(Card::getCardId).distinct().count() < 3) {
					final Card nextCard = cards.removeFirst();
					if (draftChoices.stream().anyMatch(c -> Objects.equals(c.getCardId(), nextCard.getCardId()))) {
						continue;
					}
					draftChoices.add(nextCard);
					card_i++;
				}
			}

			draftCards.add(draftChoices);
		}
		return draftCards;
	}

	private float roll() {
		return getRandom().nextFloat();
	}

	private Random getRandom() {
		return getContext().getPrivateState().random;
	}

	public void selectCard(int choiceIndex) {
		final PublicDraftState publicState = getContext().getPublicState();
		final List<Card> selectedCards = publicState.selectedCards;
		final List<List<Card>> choices = getContext().getPrivateState().cards;

		// Are we making an invalid choice?
		int draftIndex = getContext().getPublicState().draftIndex;
		if (choiceIndex >= choices.get(draftIndex).size()
				|| choiceIndex < 0) {
			throw new DraftError();
		}

		Card chosenCard = choices.get(draftIndex).get(choiceIndex);
		selectedCards.add(chosenCard);


		publicState.cardsRemaining--;
		publicState.draftIndex++;

		if (isDraftOver()) {
			publicState.currentCardChoices = Collections.emptyList();
			publicState.status = DraftStatus.COMPLETE;
		} else {
			publicState.currentCardChoices = getContext().getPrivateState().cards.get(publicState.draftIndex);
		}

		notifyPublicStateChanged();
	}

	private void notifyPublicStateChanged() {
		getContext().notifyPublicStateChanged();
	}

	public boolean isDraftOver() {
		return getContext().getPublicState().cardsRemaining == 0;
	}

	public DraftContext getContext() {
		return context.get();
	}

	public List<Card> getCardChoices() {
		return getContext().getPublicState().currentCardChoices;
	}
}
