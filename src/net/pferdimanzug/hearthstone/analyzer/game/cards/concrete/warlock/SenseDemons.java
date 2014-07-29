package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardCollection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SenseDemons extends SpellCard {

	public SenseDemons() {
		super("Sense Demons", Rarity.COMMON, HeroClass.WARLOCK, 3);
		setDescription("Put 2 random Demons from your deck into your hand.");
		setSpell(new SenseDemonsSpell());
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 349;
	}

	private class SenseDemonsSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			CardCollection demonCards = SpellUtils.getCards(player.getDeck(), card -> card.getTag(GameTag.RACE) == Race.DEMON);
			for (int i = 0; i < 2; i++) {
				Card demonCard = null;
				if (demonCards.isEmpty()) {
					demonCard = new WorthlessImp();

				} else {
					demonCard = demonCards.getRandom();
					demonCards.remove(demonCard);
					player.getDeck().remove(demonCard);
				}
				context.getLogic().receiveCard(player.getId(), demonCard);
			}
		}
	}

	private class WorthlessImp extends MinionCard {

		public WorthlessImp() {
			super("Worthless Imp", 1, 1, Rarity.COMMON, HeroClass.WARLOCK, 1);
			setDescription("You are out of demons! At least there are always imps...");
			setRace(Race.DEMON);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}
}
