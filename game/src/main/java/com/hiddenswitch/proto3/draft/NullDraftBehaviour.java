package com.hiddenswitch.proto3.draft;

import com.hiddenswitch.proto3.net.util.Result;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.heroes.HeroClass;

import java.util.List;

/**
 * Created by bberman on 12/14/16.
 */
public class NullDraftBehaviour implements DraftBehaviour {
	@Override
	public void chooseHeroAsync(List<HeroClass> classes, Handler<AsyncResult<HeroClass>> result) {
		result.handle(new Result<>(classes.get(0)));
	}

	@Override
	public void chooseCardAsync(List<Card> cards, Handler<AsyncResult<Integer>> selectedCardIndex) {
		selectedCardIndex.handle(new Result<>(0));
	}

	@Override
	public void notifyDraftState(PublicDraftState state) {
	}

	@Override
	public void notifyDraftStateAsync(PublicDraftState state, Handler<AsyncResult<Void>> acknowledged) {
	}
}
