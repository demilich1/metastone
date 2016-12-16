package net.demilich.metastone.gui.draftmode;

import javafx.scene.input.MouseEvent;
import net.demilich.metastone.gui.deckbuilder.CardListView;

/**
 * Created by bberman on 12/15/16.
 */
public class DraftCardListView extends CardListView {

	@Override
	public void handle(MouseEvent event) {
		// Do not delete cards when cards are clicked in the draft card list view
		return;
	}
}
