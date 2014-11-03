package net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic.IGameStateHeuristic;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class ThreatBasedHeuristic implements IGameStateHeuristic {

	private static ThreatLevel calcuateThreatLevel(GameContext context, int playerId) {
		int damageOnBoard = 0;
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		for (Minion minion : opponent.getMinions()) {
			damageOnBoard += minion.getAttack() * minion.getTagValue(GameTag.NUMBER_OF_ATTACKS);
		}
		damageOnBoard += getHeroDamage(opponent.getHero());

		int hpDiff = player.getHero().getEffectiveHp() - damageOnBoard;
		if (hpDiff <= 0) {
			return ThreatLevel.RED;
		} else if (hpDiff <= 14) {
			return ThreatLevel.YELLOW;
		}

		return ThreatLevel.GREEN;
	}

	private static float calculateMinionScore(Minion minion, ThreatLevel threatLevel) {
		float minionScore = 1 + minion.getAttack() + minion.getHp();
		if (minion.hasStatus(GameTag.FROZEN)) {
			return minion.getHp();
		}
		if (minion.hasStatus(GameTag.TAUNT)) {
			switch (threatLevel) {
			case RED:
				minionScore += 8;
				break;
			case YELLOW:
				minionScore += 4;
				break;
			default:
				minionScore += 2;
				break;
			}
		}
		if (minion.hasStatus(GameTag.WINDFURY)) {
			minionScore += 2;
		}
		if (minion.hasStatus(GameTag.DIVINE_SHIELD)) {
			minionScore += 1.5f;
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
			minionScore += 1.5f;
		}

		return minionScore;
	}

	private static int getHeroDamage(Hero hero) {
		int heroDamage = 0;
		if (hero.getHeroClass() == HeroClass.MAGE) {
			heroDamage += 1;
		} else if (hero.getHeroClass() == HeroClass.HUNTER) {
			heroDamage += 2;
		} else if (hero.getHeroClass() == HeroClass.DRUID) {
			heroDamage += 1;
		}
		if (hero.getWeapon() != null) {
			heroDamage += hero.getWeapon().getDurability();
		}
		return heroDamage;
	}

	@Override
	public double getScore(GameContext context, int playerId) {
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		if (player.getHero().isDead()) {
			return Float.NEGATIVE_INFINITY;
		}
		if (opponent.getHero().isDead()) {
			return Float.POSITIVE_INFINITY;
		}
		double score = 0;

		ThreatLevel threatLevel = calcuateThreatLevel(context, playerId);
		switch (threatLevel) {
		case RED:
			score -= 50;
			break;
		case YELLOW:
			score -= 10;
			break;
		default:
			break;
		}
		score += player.getHero().getEffectiveHp() - opponent.getHero().getEffectiveHp();
		int cardDiff = player.getHand().getCount() - opponent.getHand().getCount();
		score += cardDiff * 3;

		for (Minion minion : player.getMinions()) {
			score += calculateMinionScore(minion, threatLevel);
		}

		for (Minion minion : opponent.getMinions()) {
			score -= calculateMinionScore(minion, threatLevel);
		}

		return score;
	}

	@Override
	public void onActionSelected(GameContext context, int playerId) {

	}

}
