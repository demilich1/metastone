package net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic;

import java.util.HashMap;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.utils.MathUtils;

public class TDWeightHeuristic implements IGameStateHeuristic {

	private static final float ALPHA = 0.1f;

	public static final HashMap<WeightedFeature, Double> weights = new HashMap<>();

	{
		weights.put(WeightedFeature.HP_DIFFERENCE, 29.6);
		weights.put(WeightedFeature.MINION_ATTACK, 30.2);
		weights.put(WeightedFeature.MINION_DIVINE_SHIELD, 9.5);
		weights.put(WeightedFeature.MINION_HP, 31.9);
		weights.put(WeightedFeature.MINION_SPELL_POWER, 0.31);
		weights.put(WeightedFeature.MINION_STEALTHED, 0.44);
		weights.put(WeightedFeature.MINION_TAUNT, 3.3);
		weights.put(WeightedFeature.MINION_UNTARGETABLE_BY_SPELLS, 0.81);
		weights.put(WeightedFeature.MINION_WINDFURY, 0.48);
		weights.put(WeightedFeature.CARD_DIFFERENCE, -4.4);
		weights.put(WeightedFeature.OPPONENT_MINION_COUNT, -2.00);
		weights.put(WeightedFeature.OWN_MINION_COUNT, 14.5);
	}

	private final HashMap<WeightedFeature, Double> currentValues = new HashMap<>();

	private double oldGameStateValue;

	public TDWeightHeuristic() {
	}

	private double calculateMinionScore(Minion minion) {
		double minionScore = registerFeatureValue(WeightedFeature.MINION_ATTACK, minion.getAttack());
		minionScore += registerFeatureValue(WeightedFeature.MINION_HP, minion.getHp());
		float baseScore = minion.getAttack() + minion.getHp();

		if (minion.hasTag(GameTag.TAUNT)) {
			minionScore += registerFeatureValue(WeightedFeature.MINION_TAUNT, baseScore);
		}
		if (minion.hasTag(GameTag.WINDFURY)) {
			minionScore += registerFeatureValue(WeightedFeature.MINION_WINDFURY, baseScore);
		}
		if (minion.hasTag(GameTag.DIVINE_SHIELD)) {
			minionScore += registerFeatureValue(WeightedFeature.MINION_DIVINE_SHIELD, baseScore);
		}
		if (minion.hasTag(GameTag.SPELL_POWER)) {
			minionScore += registerFeatureValue(WeightedFeature.MINION_SPELL_POWER, minion.getTagValue(GameTag.SPELL_POWER));
		}

		if (minion.hasTag(GameTag.STEALTHED)) {
			minionScore += registerFeatureValue(WeightedFeature.MINION_STEALTHED, baseScore);
		}
		if (minion.hasTag(GameTag.UNTARGETABLE_BY_SPELLS)) {
			minionScore += registerFeatureValue(WeightedFeature.MINION_UNTARGETABLE_BY_SPELLS, baseScore);
		}

		return minionScore;
	}

	private double getCurrentValue(WeightedFeature feature) {
		return currentValues.containsKey(feature) ? currentValues.get(feature) : 0f;
	}

	@Override
	public double getScore(GameContext context, int playerId) {
		double score = 0;
		currentValues.clear();
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		if (player.getHero().isDead()) {
			return -1;
		}
		if (opponent.getHero().isDead()) {
			return 1;
		}
		int ownHp = player.getHero().getHp() + player.getHero().getArmor();
		int opponentHp = opponent.getHero().getHp() + opponent.getHero().getArmor();
		int hpDifference = (ownHp - opponentHp);
		score += registerFeatureValue(WeightedFeature.HP_DIFFERENCE, hpDifference);

		int cardDifference = player.getHand().getCount() - opponent.getHand().getCount();
		score += registerFeatureValue(WeightedFeature.CARD_DIFFERENCE, player.getHand().getCount());
		score += registerFeatureValue(WeightedFeature.OWN_MINION_COUNT, player.getMinions().size());
		score += registerFeatureValue(WeightedFeature.OPPONENT_MINION_COUNT, opponent.getMinions().size());
		for (Minion minion : player.getMinions()) {
			score += calculateMinionScore(minion);
		}
		for (Minion minion : opponent.getMinions()) {
			score -= calculateMinionScore(minion);
		}

		return MathUtils.clamp(score, -1, 1);
	}

	public synchronized void onActionSelected(GameContext context, int playerId) {
		double newGameStateValue = getScore(context, playerId);

		for (WeightedFeature feature : weights.keySet()) {
			double weight = weights.get(feature);
			double weightBefore = weight;
			weight += ALPHA * (newGameStateValue - oldGameStateValue) * getCurrentValue(feature);
			weights.put(feature, weight);
		}
		oldGameStateValue = newGameStateValue;
	}

	private double registerFeatureValue(WeightedFeature feature, double value) {
		if (!currentValues.containsKey(feature)) {
			currentValues.put(feature, 0.0);
		}
		currentValues.put(feature, currentValues.get(feature) + value);
		return value * weights.get(feature);
	}

}
