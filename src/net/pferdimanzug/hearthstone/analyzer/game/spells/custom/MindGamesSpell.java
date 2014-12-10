package net.pferdimanzug.hearthstone.analyzer.game.spells.custom;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.CardLocation;

public class MindGamesSpell extends Spell {
	
	public static SpellDesc create() {
		SpellDesc desc = new SpellDesc(MindGamesSpell.class);
		return desc;
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		Player opponent = context.getOpponent(player);
		MinionCard minionCard = (MinionCard) opponent.getDeck().getRandomOfType(CardType.MINION);
		if (minionCard == null) {
			minionCard = new ShadowOfNothing();
		}
		context.getLogic().removeCard(player.getId(), minionCard);
		SpellDesc summonSpell = SummonSpell.create(minionCard);
		context.getLogic().castSpell(player.getId(), summonSpell);
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
