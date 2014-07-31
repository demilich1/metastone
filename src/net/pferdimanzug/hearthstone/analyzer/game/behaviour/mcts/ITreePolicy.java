package net.pferdimanzug.hearthstone.analyzer.game.behaviour.mcts;


interface ITreePolicy {

	Node select(Node parent);
}
