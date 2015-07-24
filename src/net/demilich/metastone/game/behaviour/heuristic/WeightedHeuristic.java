package net.demilich.metastone.game.behaviour.heuristic;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.minions.Minion;

public class WeightedHeuristic implements IGameStateHeuristic {

	private float calculateMinionScore(Minion minion) {
		float minionScore = minion.getAttack() + minion.getHp();
		float baseScore = minionScore;
		if (minion.hasTag(GameTag.FROZEN)) {
			return minion.getHp();
		}
		if (minion.hasTag(GameTag.TAUNT)) {
			minionScore += 2;
		}
		if (minion.hasTag(GameTag.WINDFURY)) {
			minionScore += minion.getAttack() * 0.5f;
		}
		if (minion.hasTag(GameTag.DIVINE_SHIELD)) {
			minionScore += 1.5f * baseScore;
		}
		if (minion.hasTag(GameTag.SPELL_DAMAGE)) {
			minionScore += minion.getTagValue(GameTag.SPELL_DAMAGE);
		}
		if (minion.hasTag(GameTag.ENRAGED)) {
			minionScore += 1;
		}
		if (minion.hasTag(GameTag.STEALTH)) {
			minionScore += 1;
		}
		if (minion.hasTag(GameTag.UNTARGETABLE_BY_SPELLS)) {
			minionScore += 1.5f * baseScore;
		}
		
		return minionScore;
	}
	
	@Override
	public double getScore(GameContext context, int playerId) {
		float score = 0;
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		if (player.getHero().isDestroyed()) {
			return Float.NEGATIVE_INFINITY;
		} 
		if (opponent.getHero().isDestroyed()) {
			return Float.POSITIVE_INFINITY;
		} 
		int ownHp = player.getHero().getHp() + player.getHero().getArmor();
		int opponentHp = opponent.getHero().getHp() + opponent.getHero().getArmor();
		score += ownHp - opponentHp;
		
		score += player.getHand().getCount() * 3;
		score -= opponent.getHand().getCount() * 3;
		score += player.getMinions().size() * 2;
		score -= opponent.getMinions().size() * 2;
		for (Minion minion : player.getMinions()) {
			score += calculateMinionScore(minion);
		}
		for (Minion minion : opponent.getMinions()) {
			score -= calculateMinionScore(minion);
		}
		
		return score;
	}

	@Override
	public void onActionSelected(GameContext context, int playerId) {
	}

}
