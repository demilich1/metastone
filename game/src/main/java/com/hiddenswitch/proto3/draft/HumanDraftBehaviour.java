package com.hiddenswitch.proto3.draft;

import com.hiddenswitch.proto3.net.util.Result;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import javafx.application.Platform;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.behaviour.human.DraftSelectionOptions;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.HeroCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.heroes.MetaHero;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by bberman on 12/14/16.
 */
public class HumanDraftBehaviour implements DraftBehaviour {
	private List<Card> cards;
	private Handler<AsyncResult<Integer>> chooseCardHandler;
	private Handler<AsyncResult<HeroClass>> chooseHeroHandler;

	@Override
	public void chooseHeroAsync(List<HeroClass> classes, Handler<AsyncResult<HeroClass>> result) {
		// Convert to hero cards
		this.cards = classes.stream().map(MetaHero::getHeroCard).collect(Collectors.toList());
		this.chooseHeroHandler = result;
		DraftSelectionOptions options = new DraftSelectionOptions(this, this.cards);
		NotificationProxy.sendNotification(GameNotification.HUMAN_PROMPT_FOR_DRAFT, options);
	}

	@Override
	public void chooseCardAsync(List<Card> cards, Handler<AsyncResult<Integer>> selectedCardIndex) {
		this.cards = cards;
		this.chooseCardHandler = selectedCardIndex;
		DraftSelectionOptions options = new DraftSelectionOptions(this, this.cards);
		NotificationProxy.sendNotification(GameNotification.HUMAN_PROMPT_FOR_DRAFT, options);
	}

	@Override
	public void notifyDraftState(PublicDraftState state) {
		NotificationProxy.sendNotification(GameNotification.DRAFT_STATE_UPDATE, state);
	}

	@Override
	public void notifyDraftStateAsync(PublicDraftState state, Handler<AsyncResult<Void>> acknowledged) {
		NotificationProxy.sendNotification(GameNotification.DRAFT_STATE_UPDATE, state);
	}

	public void putChosenCard(Card chosenCard) {
		// Find the chosen card GROAN
		int index = 0;
		for (int i = 0; i < cards.size(); i++) {
			if (Objects.equals(cards.get(i).getCardId(), chosenCard.getCardId())) {
				index = i;
				break;
			}
		}

		if (chosenCard instanceof HeroCard) {
			this.chooseHeroHandler.handle(new Result<>(chosenCard.getHeroClass()));
		} else {
			this.chooseCardHandler.handle(new Result<>(index));
		}
	}
}
