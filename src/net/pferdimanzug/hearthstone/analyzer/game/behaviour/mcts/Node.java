package net.pferdimanzug.hearthstone.analyzer.game.behaviour.mcts;

import java.util.LinkedList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.PlayRandomBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

class Node {

	private GameContext state;
	private List<Transition> validTransitions;
	private final List<Node> children = new LinkedList<>();
	private final Transition incomingTransition;
	private int visits;
	private int score;
	private final int player;

	public Node(Transition incomingTransition, int player) {
		this.incomingTransition = incomingTransition;
		this.player = player;
	}

	public GameContext getState() {
		return state;
	}

	public void initState(GameContext state, List<GameAction> validActions) {
		this.state = state;
		this.validTransitions = Transition.generate(validActions);
	}

	public boolean isExpandable() {
		if (validTransitions.isEmpty()) {
			return false;
		}
		if (state.gameDecided()) {
			return false;
		}
		return getChildren().size() < validTransitions.size();
	}

	public int getScore() {
		return score;
	}

	public int getVisits() {
		return visits;
	}

	private boolean canFurtherExpanded() {
		return !validTransitions.isEmpty();
	}

	private boolean isTerminal() {
		return state.gameDecided();
	}

	private Node expand() {
		Transition transition = validTransitions.remove(0);
		GameAction action = transition.getAction();
		Entity target = transition.getTarget();
		GameContext newState = state.clone();
		if (target != null) {
			action.setTarget(target);
		}
		try {
			newState.getLogic().performGameAction(newState.getActivePlayer().getId(), action);
		} catch (Exception e) {
			System.err.println("Exception on action: " + action + " state decided: " + state.gameDecided());
			e.printStackTrace();
			throw e;
		}

		Node child = new Node(transition, getPlayer());
		child.initState(newState, newState.getValidActions());
		children.add(child);
		return child;
	}

	public void process(ITreePolicy treePolicy) {
		List<Node> visited = new LinkedList<Node>();
		Node current = this;
		visited.add(this);
		while (!current.isTerminal()) {
			if (current.canFurtherExpanded()) {
				current = current.expand();
				visited.add(current);
				break;
			} else {
				current = treePolicy.select(current);
				visited.add(current);
			}
		}

		int value = rollOut(current);
		for (Node node : visited) {
			node.updateStats(value);
		}
	}

	public boolean isLeaf() {
		return children == null || children.isEmpty();
	}

	public int rollOut(Node node) {
		if (node.getState().gameDecided()) {
			return node.getState().getScore(getPlayer());
		}

		GameContext simulation = node.getState().clone();
		for (Player player : simulation.getPlayers()) {
			player.setBehaviour(new PlayRandomBehaviour());
		}
		// if this state was reached by performing 'End Turn' then we need to start the new turn first
		if (node.incomingTransition.getAction().getActionType() == ActionType.END_TURN) {
			simulation.startTurn(simulation.getActivePlayer().getId());
		} else {
			simulation.playTurn();
		}
		
		return simulation.getScore(getPlayer());
	}

	private void updateStats(int value) {
		visits++;
		score += value;
	}

	public List<Node> getChildren() {
		return children;
	}

	public Transition getBestAction() {
		Transition best = null;
		int bestScore = Integer.MIN_VALUE;
		for (Node node : children) {
			if (node.getVisits() > bestScore) {
				best = node.incomingTransition;
				bestScore = node.getScore();
			}
		}
		return best;
	}

	public int getPlayer() {
		return player;
	}

}
