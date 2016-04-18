package net.demilich.metastone.game.behaviour.threat;

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
		defaultVector.set(WeightedFeature.HARD_REMOVAL_VALUE, 2);
		return defaultVector;
	}

	public static FeatureVector getFittest() {
		FeatureVector defaultVector = new FeatureVector();
		defaultVector.set(WeightedFeature.RED_MODIFIER, -43);
		defaultVector.set(WeightedFeature.YELLOW_MODIFIER, -17);
		defaultVector.set(WeightedFeature.OWN_HP_FACTOR, 0.214);
		defaultVector.set(WeightedFeature.OPPONENT_HP_FACTOR, -1.115);
		defaultVector.set(WeightedFeature.OWN_CARD_COUNT, 3.572);
		defaultVector.set(WeightedFeature.OPPONENT_CARD_COUNT, 0);
		defaultVector.set(WeightedFeature.MINION_INTRINSIC_VALUE, 1.181);
		defaultVector.set(WeightedFeature.MINION_ATTACK_FACTOR, 2.419);
		defaultVector.set(WeightedFeature.MINION_HP_FACTOR, 3);
		defaultVector.set(WeightedFeature.MINION_RED_TAUNT_MODIFIER, 10.1);
		defaultVector.set(WeightedFeature.MINION_YELLOW_TAUNT_MODIFIER, 7.1);
		defaultVector.set(WeightedFeature.MINION_DEFAULT_TAUNT_MODIFIER, 0.671);
		defaultVector.set(WeightedFeature.MINION_WINDFURY_MODIFIER, 15.71);
		defaultVector.set(WeightedFeature.MINION_DIVINE_SHIELD_MODIFIER, 6.1);
		defaultVector.set(WeightedFeature.MINION_SPELL_POWER_MODIFIER, 3.841);
		defaultVector.set(WeightedFeature.MINION_STEALTHED_MODIFIER, 1.281);
		defaultVector.set(WeightedFeature.MINION_UNTARGETABLE_BY_SPELLS_MODIFIER, 0);
		defaultVector.set(WeightedFeature.HARD_REMOVAL_VALUE, 2);
		return defaultVector;
	}

	private final Map<WeightedFeature, Double> values = new EnumMap<WeightedFeature, Double>(WeightedFeature.class);

	public FeatureVector() {
		for (WeightedFeature feature : WeightedFeature.values()) {
			set(feature, 0);
		}
	}

	@Override
	public FeatureVector clone() {
		FeatureVector clone = new FeatureVector();
		for (WeightedFeature feature : getValues().keySet()) {
			clone.set(feature, get(feature));
		}
		return clone;
	}

	public double get(WeightedFeature param) {
		return values.get(param);
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
