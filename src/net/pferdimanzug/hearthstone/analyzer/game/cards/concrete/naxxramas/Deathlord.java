package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Deathlord extends MinionCard {

	public Deathlord() {
		super("Deathlord", 2, 8, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Taunt. Deathrattle: Your opponent puts a minion from their deck into the battlefield.");
	}

	@Override
	public int getTypeId() {
		return 405;
	}
	
	@Override
	public Minion summon() {
		Minion deathlord = createMinion(GameTag.TAUNT);
		Spell deathlordSpell = new DeathlordSpell();
		deathlordSpell.setTarget(EntityReference.NONE);
		deathlord.addDeathrattle(deathlordSpell);
		return deathlord;
	}

	private class DeathlordSpell extends Spell {

		@Override
		protected void onCast(GameContext context, Player player, Entity target) {
			Player opponent = context.getOpponent(player);
			MinionCard minionCard = (MinionCard) opponent.getDeck().getRandomOfType(CardType.MINION);
			if (minionCard == null) {
				return;
			}
			opponent.getDeck().remove(minionCard);
			SummonSpell summonSpell = new SummonSpell(minionCard);
			context.getLogic().castSpell(player.getId(), summonSpell);
		}
		
	}
}
