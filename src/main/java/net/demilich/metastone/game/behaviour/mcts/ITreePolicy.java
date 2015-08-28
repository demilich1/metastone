package net.demilich.metastone.game.behaviour.mcts;

interface ITreePolicy {

	Node select(Node parent);
}
