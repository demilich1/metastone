package net.pferdimanzug.hearthstone.analyzer.game.behaviour.mcts;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.PlayRandomBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

class Node {

	private GameContext state;
	private List<Transition> validTransitions;
	private HashMap<Transition, Node> children = new HashMap<Transition, Node>();
	private final Node parent;
	private int visits;
	private int score;
	private boolean expanded;

	public Node(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}

	public GameContext getState() {
		return state;
	}

	public void initState(GameContext state, List<GameAction> validActions) {
		this.state = state;
		this.validTransitions = Transition.generate(validActions);
	}

	public boolean isExpandable() {
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

	public void process(ITreePolicy treePolicy) {
		List<Node> visited = new LinkedList<Node>();
		Node current = this;
		visited.add(this);
		while (!current.isLeaf() && current.isExpandable()) {
			current = treePolicy.select(current);
			visited.add(current);
		}
		
		current.expand();
		if (current.getChildren().size() > 0) {
			current = treePolicy.select(current);
			visited.add(current);
		}
		

		int value = rollOut(current);
		for (Node node : visited) {
			node.updateStats(value);
		}
	}

	public void expand() {
		for (Transition transition : validTransitions) {
			GameAction action = transition.getAction();
			Entity target = transition.getTarget();
			GameContext newState = state.clone();
			if (target != null) {
				action.setTarget(target);
			}
			newState.getLogic().performGameAction(newState.getActivePlayer().getId(), action);
			Node child = new Node(this);
			child.initState(newState, newState.getValidActions());
			children.put(transition, child);
		}
		expanded = true;
	}

	public boolean isLeaf() {
		return !expanded;
	}

	public int rollOut(Node node) {
		GameContext simulation = node.getState();
		int playerId = simulation.getActivePlayer().getId();
		for (Player player : simulation.getPlayers()) {
			player.setBehaviour(new PlayRandomBehaviour());
		}

		simulation.playTurn();
		return simulation.isWinner(playerId) ? 1 : -1;
	}

	private void updateStats(int value) {
		visits++;
		score += value;
	}

	public HashMap<Transition, Node> getChildren() {
		return children;
	}
	
	public Transition getBestAction() {
		Transition best = null;
		int bestScore = Integer.MIN_VALUE;
		for (Transition transition : children.keySet()) {
			Node node = children.get(transition);
			if (node.getScore() > bestScore) {
				best = transition;
				bestScore = node.getScore();
			}
		}
		return best;
	}

}
