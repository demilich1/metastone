package net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class WeightedHeuristic implements IGameStateHeuristic {

	private float calculateMinionScore(Minion minion) {
		float minionScore = minion.getAttack() + minion.getHp();
		float baseScore = minionScore;
		if (minion.hasStatus(GameTag.FROZEN)) {
			return minion.getHp();
		}
		if (minion.hasStatus(GameTag.TAUNT)) {
			minionScore += 2;
		}
		if (minion.hasStatus(GameTag.WINDFURY)) {
			minionScore += minion.getAttack() * 0.5f;
		}
		if (minion.hasStatus(GameTag.DIVINE_SHIELD)) {
			minionScore += 1.5f * baseScore;
		}
		if (minion.hasStatus(GameTag.SPELL_POWER)) {
			minionScore += minion.getTagValue(GameTag.SPELL_POWER);
		}
		if (minion.hasStatus(GameTag.ENRAGED)) {
			minionScore += 1;
		}
		if (minion.hasStatus(GameTag.STEALTHED)) {
			minionScore += 1;
		}
		if (minion.hasStatus(GameTag.UNTARGETABLE_BY_SPELLS)) {
			minionScore += 1.5f * baseScore;
		}
		
		return minionScore;
	}
	
	@Override
	public double getScore(GameContext context, int playerId) {
		float score = 0;
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		if (player.getHero().isDead()) {
			return Float.NEGATIVE_INFINITY;
		} 
		if (opponent.getHero().isDead()) {
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
