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
	private HashMap<Transition, Node> children;
	private int visits;
	private int score;
	private final String name;
	private final int player;

	public Node(GameAction transitionAction, int player) {
		this.name = transitionAction != null ? transitionAction.toString() : "root";
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

	static int runs;

	public void process(ITreePolicy treePolicy) {
		List<Node> visited = new LinkedList<Node>();
		Node current = this;
		visited.add(this);
		runs++;
		while (!current.isLeaf()) {
			Node old = current;
			current = treePolicy.select(current);
			if (current == null) {
				System.out.println("Parent: " + old.name + " Children " + old.getChildren().size() + " Transitions: "
						+ old.validTransitions.size() + " GameState decided: " + old.state.gameDecided() + " runs: " + runs);
			}
			visited.add(current);
		}

		current.expand();
		if (!current.isLeaf()) {
			current = treePolicy.select(current);
			visited.add(current);
		}
		
		int value = rollOut(current);
		for (Node node : visited) {
			node.updateStats(value);
		}
	}

	public void expand() {
		children = new HashMap<Transition, Node>();
		if (state.gameDecided()) {
			return;
		}
		for (Transition transition : validTransitions) {
			GameAction action = transition.getAction();
			Entity target = transition.getTarget();
			GameContext newState = state.clone();
			if (target != null) {
				action.setTarget(target);
			}
			try {
				newState.getLogic().performGameAction(newState.getActivePlayer().getId(), action);
			} catch(Exception e) {
				System.err.println("Exception on action: " + action + " state decided: " + state.gameDecided());
				e.printStackTrace();
				throw e;
			}
			
			Node child = new Node(action, getPlayer());
			child.initState(newState, newState.getValidActions());
			children.put(transition, child);
		}
	}

	public boolean isLeaf() {
		return children == null || children.isEmpty();
	}

	public int rollOut(Node node) {
		GameContext simulation = node.getState().clone();
		for (Player player : simulation.getPlayers()) {
			player.setBehaviour(new PlayRandomBehaviour());
		}

		simulation.playTurn();
		return simulation.isWinner(getPlayer()) ? 1 : -1;
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

	public int getPlayer() {
		return player;
	}

}
