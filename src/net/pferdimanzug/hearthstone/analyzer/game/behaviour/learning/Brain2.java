package net.pferdimanzug.hearthstone.analyzer.game.behaviour.learning;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.utils.MathUtils;

import org.encog.ml.CalculateScore;
import org.encog.ml.MLMethod;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.anneal.NeuralSimulatedAnnealing;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.util.simple.EncogUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Brain2 implements IBrain {

	private static Logger logger = LoggerFactory.getLogger(Brain2.class);

	private static final int INPUTS = 51;
	private static final int HIDDEN_NEURONS = 40;
	private static final int OUTPUTS = 1;

	private static final double ALPHA = 0.1;
	private static final double LAMBDA = 0.01;

	private BasicNetwork neuralNetwork;
	private boolean learning;

	public Brain2() {
		// Create a neural network, using the utility.
		neuralNetwork = EncogUtility.simpleFeedForward(INPUTS, HIDDEN_NEURONS, 0, 1, false);
		neuralNetwork.reset();
	}

	@Override
	public double[] getOutput(GameContext context, int playerId) {
		double[] input = gameStateToInput(context, playerId);
		double[] output = new double[OUTPUTS];
		neuralNetwork.compute(input, output);
		return output;
	}

	private double[] gameStateToInput(GameContext context, int playerId) {
		double[] input = new double[INPUTS];
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		encodePlayer(player, input, 0);
		encodePlayer(opponent, input, INPUTS / 2);
		input[50] = MathUtils.clamp01(context.getTurn() / 20.0);
		return input;
	}

	private void encodePlayer(Player player, double[] data, int offset) {
		List<Minion> minions = player.getMinions();
		for (int i = 0; i < 7; i++) {
			Minion minion = i < minions.size() ? player.getMinions().get(i) : null;
			data[offset++] = minion != null ? MathUtils.clamp01(minion.getAttack() / 15.0) : -1;
			data[offset++] = minion != null ? MathUtils.clamp01(minion.getHp() / 15.0) : -1;
			data[offset++] = minion != null ? (minion.getSpellTrigger() != null ? 1 : -1) : -1;
		}
		data[offset++] = MathUtils.clamp01(player.getHero().getAttack() / 10.0);
		data[offset++] = MathUtils.clamp01((player.getHero().getHp() + player.getHero().getArmor()) / 30.0);
		data[offset++] = player.getHand().getCount() / 10.0;
		data[offset++] = MathUtils.clamp01(player.getDeck().getCount() / 30.0);
	}

	@Override
	public double getEstimatedUtility(double[] output) {
		return output[0];
	}

	@Override
	public void learn(GameContext originalState, int playerId, double[] nextOutput) {
		double[] input = gameStateToInput(originalState, playerId);
		double[] output = getOutput(originalState, playerId);
		double[] error = new double[OUTPUTS];

		final MLTrain train =  new NeuralSimulatedAnnealing(neuralNetwork, new CalculateScore() {
			
			@Override
			public boolean shouldMinimize() {
				return true;
			}
			
			@Override
			public boolean requireSingleThreaded() {
				return false;
			}
			
			@Override
			public double calculateScore(MLMethod method) {
				return 0;
			}
		}, 1, 0.1, 10);
		train.iteration();
	}

	@Override
	public boolean isLearning() {
		return learning;
	}

	@Override
	public void setLearning(boolean learning) {
		this.learning = learning;
	}

	@Override
	public void save(String savePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(String savePath) {
		// TODO Auto-generated method stub
		
	}

}
