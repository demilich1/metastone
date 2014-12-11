package net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat;

import java.util.EnumMap;
import java.util.Map;

public class FeatureVector implements Cloneable {

	public static FeatureVector getDefault() {
		FeatureVector defaultVector = new FeatureVector();
		defaultVector.set(WeightedFeature.RED_MODIFIER, -50);
		defaultVector.set(WeightedFeature.YELLOW_MODIFIER, -10);
		defaultVector.set(WeightedFeature.OWN_HP_FACTOR, 1);
		defaultVector.set(WeightedFeature.OPPONENT_HP_FACTOR, -1);
		defaultVector.set(WeightedFeature.OWN_CARD_COUNT, 3);
		defaultVector.set(WeightedFeature.OPPONENT_CARD_COUNT, -3);
		defaultVector.set(WeightedFeature.MINION_INTRINSIC_VALUE, 1);
		defaultVector.set(WeightedFeature.MINION_ATTACK_FACTOR, 1);
		defaultVector.set(WeightedFeature.MINION_HP_FACTOR, 1);
		defaultVector.set(WeightedFeature.MINION_RED_TAUNT_MODIFIER, 8);
		defaultVector.set(WeightedFeature.MINION_YELLOW_TAUNT_MODIFIER, 4);
		defaultVector.set(WeightedFeature.MINION_DEFAULT_TAUNT_MODIFIER, 2);
		defaultVector.set(WeightedFeature.MINION_WINDFURY_MODIFIER, 1.5);
		defaultVector.set(WeightedFeature.MINION_DIVINE_SHIELD_MODIFIER, 1.5);
		defaultVector.set(WeightedFeature.MINION_SPELL_POWER_MODIFIER, 1);
		defaultVector.set(WeightedFeature.MINION_STEALTHED_MODIFIER, 1);
		defaultVector.set(WeightedFeature.MINION_UNTARGETABLE_BY_SPELLS_MODIFIER, 1.5);
		return defaultVector;
	}

	public static FeatureVector getFittest() {
		FeatureVector defaultVector = new FeatureVector();
		defaultVector.set(WeightedFeature.RED_MODIFIER, -20);
		defaultVector.set(WeightedFeature.YELLOW_MODIFIER, -3);
		defaultVector.set(WeightedFeature.OWN_HP_FACTOR, 1);
		defaultVector.set(WeightedFeature.OPPONENT_HP_FACTOR, -50);
		defaultVector.set(WeightedFeature.OWN_CARD_COUNT, 1);
		defaultVector.set(WeightedFeature.OPPONENT_CARD_COUNT, 2);
		defaultVector.set(WeightedFeature.MINION_INTRINSIC_VALUE, 81);
		defaultVector.set(WeightedFeature.MINION_ATTACK_FACTOR, 99);
		defaultVector.set(WeightedFeature.MINION_HP_FACTOR, 42);
		defaultVector.set(WeightedFeature.MINION_RED_TAUNT_MODIFIER, 99);
		defaultVector.set(WeightedFeature.MINION_YELLOW_TAUNT_MODIFIER, 4);
		defaultVector.set(WeightedFeature.MINION_DEFAULT_TAUNT_MODIFIER, 63.1);
		defaultVector.set(WeightedFeature.MINION_WINDFURY_MODIFIER, 3);
		defaultVector.set(WeightedFeature.MINION_DIVINE_SHIELD_MODIFIER, 1.5);
		defaultVector.set(WeightedFeature.MINION_SPELL_POWER_MODIFIER, 45);
		defaultVector.set(WeightedFeature.MINION_STEALTHED_MODIFIER, 12);
		defaultVector.set(WeightedFeature.MINION_UNTARGETABLE_BY_SPELLS_MODIFIER, 1.5);
		return defaultVector;
	}

	private final Map<WeightedFeature, Double> values = new EnumMap<WeightedFeature, Double>(WeightedFeature.class);

	@Override
	public FeatureVector clone() {
		FeatureVector clone = new FeatureVector();
		for (WeightedFeature feature : getValues().keySet()) {
			clone.set(feature, get(feature));
		}
		return clone;
	}

	public double get(WeightedFeature param) {
		return getValues().get(param);
	}

	public Map<WeightedFeature, Double> getValues() {
		return values;
	}

	public void set(WeightedFeature param, double value) {
		getValues().put(param, value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[FeatureVector] Values:\n");
		for (WeightedFeature feature : getValues().keySet()) {
			builder.append("\t");
			builder.append(feature.toString());
			builder.append(": ");
			builder.append(String.valueOf(getValues().get(feature)));
			builder.append("\n");
		}
		return builder.toString();
	}

}
