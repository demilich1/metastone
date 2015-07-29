package net.demilich.metastone.tools;

import javafx.scene.Node;
import net.demilich.metastone.game.cards.desc.CardDesc;

public interface ICardEditor {

	public CardDesc getCardDesc();

	public Node getPanel();

	public void reset();

}
