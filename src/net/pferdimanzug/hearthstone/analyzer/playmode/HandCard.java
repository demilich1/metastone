package net.pferdimanzug.hearthstone.analyzer.playmode;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.stage.PopupWindow;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;

public class HandCard extends CardToken {

	public HandCard() {
		super("HandCard.fxml");
	}
	
	@Override
	public void setCard(Card card, Player player) {
		super.setCard(card, player);
		final PopupWindow tooltip = TooltipFactory.createCardTooltip(card, player);
		setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Bounds localBounds = HandCard.this.getBoundsInLocal();
				Point2D screenPos = HandCard.this.localToScene(localBounds.getMaxX() , localBounds.getMaxY());
				tooltip.show(HandCard.this, screenPos.getX() + 2 * getWidth(), screenPos.getY() - 2 * getHeight());
			}
		});
		setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				tooltip.hide();
			}
		});

		 visibleProperty().addListener(new ChangeListener<Boolean>() {
		        @Override
		        public void changed(final ObservableValue<? extends Boolean> observableValue, final Boolean aBoolean, final Boolean aBoolean2) {
		            tooltip.hide();
		        }
		    });
		
	}

}
