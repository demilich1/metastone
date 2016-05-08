package net.demilich.metastone.game.behaviour.learning;

import java.util.List;


import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.behaviour.neutralnetwork.HiddenUnit;
import net.demilich.metastone.game.behaviour.neutralnetwork.NeuralNetwork;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.utils.MathUtils;

public class Brain implements IBrain {

	//private static Logger logger = LoggerFactory.getLogger(Brain.class);

	private static final int INPUTS = 15;
	private static final int HIDDEN_NEURONS = 40;
	private static final int OUTPUTS = 1;

	private static final double ALPHA = 0.1;
	private static final double BETA = 0.1;
	private static final double LAMBDA = 0.5;

	private boolean learning;
	private NeuralNetwork neuralNetwork;
	private double[][] ew;
	private double[][][] ev;

	public Brain() {
		neuralNetwork = new NeuralNetwork(INPUTS, new int[] { HIDDEN_NEURONS, OUTPUTS });
		ew = new double[HIDDEN_NEURONS][OUTPUTS];
		ev = new double[INPUTS][HIDDEN_NEURONS][OUTPUTS];
	}

	private void backPropagation(double[] in, double[] out, double[] expected) {
		// compute eligability traces
		for (int j = 0; j < neuralNetwork.hidden[0].length; j++) {
			for (int k = 0; k < out.length; k++) {
				ew[j][k] = LAMBDA * ew[j][k] + gradient(neuralNetwork.hidden[1][k]) * neuralNetwork.hidden[0][j].getValue();
				for (int i = 0; i < in.length; i++) {
					ev[i][j][k] = LAMBDA * ev[i][j][k] + gradient(neuralNetwork.hidden[1][k]) * neuralNetwork.hidden[1][k].weights[j]
							* gradient(neuralNetwork.hidden[0][j]) * in[i];
				}
			}
		}

		double[] error = new double[out.length];
		for (int k = 0; k < out.length; k++) {
			error[k] = expected[k] - out[k];
		}

		for (int j = 0; j < neuralNetwork.hidden[0].length; j++) {
			for (int k = 0; k < out.length; k++) {
				// weight from j to k, shown with learning param of BETA
				neuralNetwork.hidden[1][k].weights[j] += BETA * error[k] * ew[j][k];
				for (int i = 0; i < in.length; i++) {
					neuralNetwork.hidden[0][j].weights[i] += ALPHA * error[k] * ev[i][j][k];
				}
			}
		}
	}

	private void encodePlayer(Player player, double[] data, int offset) {
		List<Minion> minions = player.getMinions();
		int totalMinionAttack = 0;
		int totalMinionHp = 0;
		for (int i = 0; i < 7; i++) {
			Minion minion = i < minions.size() ? player.getMinions().get(i) : null;
			totalMinionAttack += minion != null ? minion.getAttack() : 0;
			totalMinionHp += minion != null ? minion.getHp() : 0;
		}
		data[offset++] = minions.size() / 7.0;
		data[offset++] = MathUtils.clamp01(totalMinionAttack / 40.0);
		data[offset++] = MathUtils.clamp01(totalMinionHp / 40.0);
		data[offset++] = MathUtils.clamp01(player.getHero().getAttack() / 10.0);
		data[offset++] = MathUtils.clamp01((player.getHero().getHp() + player.getHero().getArmor()) / 40.0);
		data[offset++] = player.getHand().getCount() / 10.0;
		data[offset++] = MathUtils.clamp01(player.getDeck().getCount() / 30.0);
	}

	private double[] gameStateToInput(GameContext context, int playerId) {
		double[] input = new double[INPUTS];
		Player player = context.getPlayer(playerId);
		Player opponent = context.getOpponent(player);
		encodePlayer(player, input, 0);
		encodePlayer(opponent, input, INPUTS / 2);
		input[INPUTS - 1] = MathUtils.clamp01(context.getTurn() / 10.0);
		return input;
	}

	@Override
	public double getEstimatedUtility(double[] output) {
		return output[0];
	}

	@Override
	public double[] getOutput(GameContext context, int playerId) {
		double[] input = gameStateToInput(context, playerId);
		return neuralNetwork.getValue(input);
	}

	private double gradient(HiddenUnit hiddenUnit) {
		return hiddenUnit.getValue() * (1.0 - hiddenUnit.getValue());
	}

	@Override
	public boolean isLearning() {
		return learning;
	}

	@Override
	public void learn(GameContext originalState, int playerId, double[] nextOutput, double reward) {
		double[] currentInput = gameStateToInput(originalState, playerId);
		double[] currentOutput = getOutput(originalState, playerId);
		for (int i = 0; i < nextOutput.length; i++) {
			nextOutput[i] += reward;
		}
		backPropagation(currentInput, currentOutput, nextOutput);
	}

	public void load(String path) {
		// try {
		// neuralNetwork = NeuralNetwork.readFrom(path);
		// logger.info("Saved brain data loaded");
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// logger.info("Brain data not found, using unlearned neural network");
		// }

	}

	/*private void printWeights() {
		for (int i = 0; i < neuralNetwork.hidden.length; i++) {
			for (int j = 0; j < neuralNetwork.hidden[i].length; j++) {
				for (int k = 0; k < neuralNetwork.hidden[i][j].weights.length; k++) {
					System.out.println(Arrays.toString(neuralNetwork.hidden[i][j].weights));
				}
			}
		}
	}*/

	public void save(String path) {

		// try {
		// neuralNetwork.writeTo(path);
		// logger.info("Brain data saved to: " + path);
		// } catch (IOException e) {
		// }
	}

	@Override
	public void setLearning(boolean learning) {
		this.learning = learning;
	}

	public void wipeEligabilityTraces() {
		ew = new double[HIDDEN_NEURONS][OUTPUTS];
		ev = new double[INPUTS][HIDDEN_NEURONS][OUTPUTS];
	}
}
