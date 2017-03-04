package com.hiddenswitch.proto3.draft;

import net.demilich.metastone.game.cards.*;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * Created by bberman on 12/14/16.
 */
public class DraftLogic {
	private static final Logger logger = LoggerFactory.getLogger(DraftLogic.class);
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
				CardSet.ONE_NIGHT_IN_KARAZHAN);

		// Until we have enough mean streets cards, don't use it
		CardSet latestExpansion = CardSet.MEAN_STREETS_OF_GADGETZHAN;

		Set<CardType> validCardTypes = new HashSet<>(Arrays.asList(
				CardType.CHOOSE_ONE,
				CardType.MINION,
				CardType.SPELL,
				CardType.WEAPON
		));

		Set<String> bannedCards = new HashSet<>();
		bannedCards.addAll(Arrays.asList(
				"spell_forgotten_torch",
				"minion_snowchugger",
				"minion_faceless_summoner",
				"minion_goblin_auto-barber",
				"minion_undercity_valiant",
				"minion_vitality_totem",
				"minion_dust_devil",
				"spell_totemic_might",
				"spell_ancestral_healing",
				"minion_dunemaul_shaman",
				"minion_windspeaker",
				"minion_anima_golem",
				"spell_sacrificial_pact",
				"spell_curse_of_rafaam",
				"spell_sense_demons",
				"minion_void_crusher",
				"minion_reliquary_seeker",
				"minion_succubus",
				"spell_savagery",
				"spell_poison_seeds",
				"spell_soul_of_the_forest",
				"spell_mark_of_nature",
				"spell_tree_of_life",
				"spell_astral_communion",
				"minion_warsong_commander",
				"spell_bolster",
				"spell_charge",
				"spell_bouncing_blade",
				"minion_axe_flinger",
				"spell_rampage",
				"minion_ogre_warmaul",
				"minion_starving_buzzard",
				"spell_call_pet",
				"minion_timber_wolf",
				"spell_cobra_shot",
				"spell_lock_and_load",
				"secret_dart_trap",
				"secret_snipe",
				"spell_mind_blast",
				"minion_shadowbomber",
				"minion_lightwell",
				"spell_power_word_glory",
				"spell_confuse",
				"spell_convert",
				"spell_inner_fire"
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

			while (draftChoices.stream().map(Card::getCardId).distinct().count() < CARDS_PER_DRAFT) {
				float cardSetRoll = roll();
				CardSet set;
				DeckFormat format = new DeckFormat();
				float latestExpansionOdds = EXPANSION_ODDS_FACTOR / (equals.size() + EXPANSION_ODDS_FACTOR);
				if (cardSetRoll < latestExpansionOdds) {
					format.withCardSets(latestExpansion);
				} else {
					format.withCardSets(equals);
				}

				// Get neutral and hero cards
				CardCollection classCards = CardCatalogue.query(format, c -> {
					return c.hasHeroClass(hero)
							&& !bannedCards.contains(c.getCardId())
							&& c.getRarity() == rarity
							&& validCardTypes.contains(c.getCardType())
							&& c.isCollectible();
				});

				CardCollection neutralCards = CardCatalogue.query(format, c -> {
					return (c.hasHeroClass(HeroClass.ANY) || c.hasHeroClass(HeroClass.NEUTRAL))
							&& !bannedCards.contains(c.getCardId())
							&& c.getRarity() == rarity
							&& validCardTypes.contains(c.getCardType())
							&& c.isCollectible();
				});

				// Add two copies of the class cards and then the neutrals
				CardCollection cards = classCards.clone().addAll(classCards).addAll(neutralCards);

				if (cards.getCount() == 0) {
					logger.info("Draft pulled no cards given parameters: draft={}, rarity={}, sets={}", draft, rarity, format.getCardSets());
					continue;
				}

				// Shuffle then choose until we're done
				cards.shuffle(getRandom());

				while (cards.getCount() > 0
						&& draftChoices.stream().map(Card::getCardId).distinct().count() < CARDS_PER_DRAFT) {
					final Card nextCard = cards.removeFirst();

					if (draftChoices.stream().anyMatch(c -> Objects.equals(c.getCardId(), nextCard.getCardId()))) {
						continue;
					}

					draftChoices.add(nextCard);
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
