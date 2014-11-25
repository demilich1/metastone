package net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat;

import java.util.HashMap;

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
		defaultVector.set(WeightedFeature.RED_MODIFIER, 1.5);
		defaultVector.set(WeightedFeature.YELLOW_MODIFIER, -27.5);
		defaultVector.set(WeightedFeature.OWN_HP_FACTOR, 1.5);
		defaultVector.set(WeightedFeature.OPPONENT_HP_FACTOR, -3);
		defaultVector.set(WeightedFeature.OWN_CARD_COUNT, 2.274);
		defaultVector.set(WeightedFeature.OPPONENT_CARD_COUNT, -70);
		defaultVector.set(WeightedFeature.MINION_INTRINSIC_VALUE, 1);
		defaultVector.set(WeightedFeature.MINION_ATTACK_FACTOR,86.4);
		defaultVector.set(WeightedFeature.MINION_HP_FACTOR, 68);
		defaultVector.set(WeightedFeature.MINION_RED_TAUNT_MODIFIER, 70);
		defaultVector.set(WeightedFeature.MINION_YELLOW_TAUNT_MODIFIER, 3);
		defaultVector.set(WeightedFeature.MINION_DEFAULT_TAUNT_MODIFIER, 84.416);
		defaultVector.set(WeightedFeature.MINION_WINDFURY_MODIFIER, 1);
		defaultVector.set(WeightedFeature.MINION_DIVINE_SHIELD_MODIFIER, 81.46577);
		defaultVector.set(WeightedFeature.MINION_SPELL_POWER_MODIFIER, 0.1);
		defaultVector.set(WeightedFeature.MINION_STEALTHED_MODIFIER, 1);
		defaultVector.set(WeightedFeature.MINION_UNTARGETABLE_BY_SPELLS_MODIFIER, 1.5);
		return defaultVector;
	}

	private final HashMap<WeightedFeature, Double> values = new HashMap<WeightedFeature, Double>();

	public void set(WeightedFeature param, double value) {
		getValues().put(param, value);
	}

	public double get(WeightedFeature param) {
		return getValues().get(param);
	}
	
	@Override
	public FeatureVector clone() {
		FeatureVector clone = new FeatureVector();
		for (WeightedFeature feature : getValues().keySet()) {
			clone.set(feature, get(feature));
		}
		return clone;
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

	public HashMap<WeightedFeature, Double> getValues() {
		return values;
	}

}
