package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCatalogue;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Webspinner extends MinionCard {

	private class WebspinnerSpell extends Spell {

		{
			setTarget(EntityReference.NONE);
		}

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			CardCollection minionCards = CardCatalogue.query(CardType.MINION, null, null);
			CardCollection beastCards = SpellUtils.getCards(minionCards, card -> card.getTag(GameTag.RACE) == Race.BEAST);

			Card randomBeastCard = beastCards.getRandom();
			context.getLogic().receiveCard(player.getId(), randomBeastCard);
		}

	}

	public Webspinner() {
		super("Webspinner", 1, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Deathrattle: Add a random Beast card to your hand.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 412;
	}

	@Override
	public Minion summon() {
		Minion webspinner = createMinion();
		webspinner.addDeathrattle(new WebspinnerSpell());
		return webspinner;
	}
}
