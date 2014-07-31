package net.pferdimanzug.hearthstone.analyzer.game.behaviour.mcts;

import java.util.Random;

class UctPolicy implements ITreePolicy {

	private static final double EPSILON = 1e-6;
	private static final Random random = new Random();

	@Override
	public Node select(Node parent) {
		Node selected = null;
		double bestValue = Double.MIN_VALUE;
		for (Node child : parent.getChildren().values()) {
			double uctValue = child.getScore() / (child.getVisits() + EPSILON)
					+ Math.sqrt(
							Math.log(parent.getVisits() + 1) 
							/ (child.getVisits() + EPSILON)) + random.nextDouble() * EPSILON;
			// small random number to break ties randomly in unexpanded nodes
			if (uctValue > bestValue) {
				selected = child;
				bestValue = uctValue;
			}
		}
		return selected;
	}

}
