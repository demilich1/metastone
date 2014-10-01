package net.pferdimanzug.hearthstone.analyzer.game.behaviour.learning;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;

public interface IBrain {

	public abstract double[] getOutput(GameContext context, int playerId);

	public abstract double getEstimatedUtility(double[] output);

	public abstract void learn(GameContext originalState, int playerId, double[] nextOutput, double reward);

	public abstract boolean isLearning();

	public abstract void setLearning(boolean learning);

	public abstract void save(String savePath);

	public abstract void load(String savePath);

}