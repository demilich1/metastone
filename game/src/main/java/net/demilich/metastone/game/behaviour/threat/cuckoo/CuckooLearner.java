package net.demilich.metastone.game.behaviour.threat.cuckoo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import net.demilich.metastone.game.behaviour.threat.FeatureVector;
import net.demilich.metastone.game.behaviour.threat.WeightedFeature;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.utils.MathUtils;

public class CuckooLearner {

	private static int POPULATION_SIZE = 15;
	private static double DISCOVERY_RATE = 0.25;

	private static double levyClamped(double base, double min, double max) {
		int sign = Math.random() < 0.5 ? -1 : 1;
		double value = base + sign * MathUtils.levy(1, 2);
		return MathUtils.clamp(value, min, max);
	}

	private static FeatureVector levyFlight(FeatureVector base) {
		FeatureVector variant = base.clone();
		Map<WeightedFeature, Double> values = base.getValues();
		for (WeightedFeature feature : values.keySet()) {
			Double weight = values.get(feature);
			switch (feature) {
			case MINION_ATTACK_FACTOR:
			case MINION_DEFAULT_TAUNT_MODIFIER:
			case MINION_YELLOW_TAUNT_MODIFIER:
			case MINION_RED_TAUNT_MODIFIER:
			case MINION_DIVINE_SHIELD_MODIFIER:
			case MINION_INTRINSIC_VALUE:
				variant.set(feature, levyClamped(weight, -10, 100));
				break;
			case MINION_HP_FACTOR:
			case MINION_SPELL_POWER_MODIFIER:
			case MINION_STEALTHED_MODIFIER:
			case MINION_UNTARGETABLE_BY_SPELLS_MODIFIER:
			case MINION_WINDFURY_MODIFIER:
			case HARD_REMOVAL_VALUE:
				variant.set(feature, levyClamped(weight, 0, 100));
				break;
			case OWN_CARD_COUNT:
			case OWN_HP_FACTOR:
			case OPPONENT_HP_FACTOR:
			case OPPONENT_CARD_COUNT:
				variant.set(feature, levyClamped(weight, -100, 100));
				break;
			case RED_MODIFIER:
				variant.set(feature, levyClamped(weight, -100, 0));
				break;
			case YELLOW_MODIFIER:
				variant.set(feature, levyClamped(weight, -100, 10));
				break;
			default:
				break;
			}
		}
		return variant;
	}

	private static CuckooAgent newRandomSolution() {
		FeatureVector solution = levyFlight(FeatureVector.getDefault());
		return new CuckooAgent(solution);
	}

	private final List<CuckooAgent> nests;

	private final IFitnessFunction fitnessFunction;

	private CuckooAgent fittest;

	public CuckooLearner(Deck deckToTrain, List<Deck> decks) {
		nests = new ArrayList<CuckooAgent>(POPULATION_SIZE);
		for (int i = 0; i < POPULATION_SIZE; i++) {
			nests.add(newRandomSolution());
		}
		fittest = nests.get(0);

		fitnessFunction = new WinRateFitness(deckToTrain, decks);
	}

	public void evolve() {
		// pick a random solution
		int i = ThreadLocalRandom.current().nextInt(POPULATION_SIZE);
		// change it randomly
		System.out.println("Random nest picked: " + i);
		FeatureVector newBreed = levyFlight(nests.get(i).getData());
		CuckooAgent cuckoo = new CuckooAgent(newBreed);
		double fitnessI = fitnessFunction.evaluate(newBreed);
		System.out.println("Fitness I: " + fitnessI);
		cuckoo.setFitness(fitnessI);
		int j = ThreadLocalRandom.current().nextInt(POPULATION_SIZE);
		CuckooAgent randomNest = nests.get(j);
		double fitnessJ = fitnessFunction.evaluate(randomNest.getData());
		System.out.println("Fitness J: " + fitnessJ);
		randomNest.setFitness(fitnessJ);

		// if new solution is better than old one, replace it
		if (fitnessI > fitnessJ) {
			System.out.println("New solution is better! Replacing old one...");
			nests.remove(j);
			nests.add(cuckoo);
		}

		// rank all existing solutions
		for (CuckooAgent nest : nests) {
			nest.setFitness(fitnessFunction.evaluate(nest.getData()));
		}
		Collections.sort(nests);
		// discard a portion of the worst ones
		int discardAmount = (int) (POPULATION_SIZE * DISCOVERY_RATE);
		for (int k = 0; k < discardAmount; k++) {
			CuckooAgent notFitEnough = nests.remove(nests.size() - 1);
			System.out.println("Solution with fitness value " + notFitEnough.getFitness() + " has been removed");
		}
		// fill up with new
		while (nests.size() < POPULATION_SIZE) {
			CuckooAgent newBirdInTown = newRandomSolution();
			newBirdInTown.setFitness(fitnessFunction.evaluate(newBirdInTown.getData()));
			nests.add(newBirdInTown);
		}
		Collections.sort(nests);
		fittest = nests.get(0);
		System.out.println("New best solution: " + fittest.getData());
		System.out.println("Fitness value: " + fittest.getFitness());
	}

	public FeatureVector getFittest() {
		return fittest.getData();
	}

}