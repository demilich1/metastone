package net.pferdimanzug.hearthstone.analyzer.game.behaviour.mcts;

import java.util.Random;

class UctPolicy implements ITreePolicy {

	private static final double EPSILON = 1e-5;
	private static final Random random = new Random();
	
	private static final double C = 1.414;

	@Override
	public Node select(Node parent) {
		Node selected = null;
		double bestValue = Double.NEGATIVE_INFINITY;
		for (Node child : parent.getChildren().values()) {
			double uctValue = child.getScore() / (child.getVisits() + EPSILON)
					+ C * Math.sqrt(
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
