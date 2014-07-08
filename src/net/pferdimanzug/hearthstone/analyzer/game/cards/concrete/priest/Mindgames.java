package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Mindgames extends SpellCard {

	public Mindgames() {
		super("Mindgames", Rarity.EPIC, HeroClass.PRIEST, 4);
		setDescription("Put a copy of a random minion from your opponent's deck into the battlefield.");
		
		setSpell(new MindGamesSpell());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	private class MindGamesSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Player opponent = context.getOpponent(player);
			MinionCard minionCard = (MinionCard) opponent.getDeck().getRandomOfType(CardType.MINION);
			if (minionCard == null) {
				minionCard = new ShadowOfNothing();
			}
			SummonSpell summonSpell = new SummonSpell(minionCard);
			context.getLogic().castSpell(player.getId(), summonSpell);
		}
		
	}
	
	private class ShadowOfNothing extends MinionCard {

		public ShadowOfNothing() {
			super("Shadow of Nothing", 0, 1, Rarity.EPIC, HeroClass.PRIEST, 0);
			setDescription("Mindgames whiffed! Your opponent had no minions!");
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}
		
	}

}
