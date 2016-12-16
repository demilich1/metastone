package com.hiddenswitch.proto3.draft;

import com.hiddenswitch.proto3.net.common.Recursive;
import com.hiddenswitch.proto3.net.util.Result;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import net.demilich.metastone.game.entities.heroes.HeroClass;

import java.util.function.Consumer;

/**
 * Created by bberman on 12/14/16.
 */
public class DraftContext implements Consumer<Handler<AsyncResult<DraftContext>>> {
	private DraftLogic logic = new DraftLogic(this);
	private PublicDraftState publicState = new PublicDraftState();
	private PrivateDraftState privateState = new PrivateDraftState();
	private DraftBehaviour behaviour = new NullDraftBehaviour();
	private Handler<AsyncResult<DraftContext>> handleDone;

	public DraftContext() {
	}

	public void accept(Handler<AsyncResult<DraftContext>> done) {
		if (handleDone != null) {
			throw new RuntimeException("Stale draft context.");
		}

		handleDone = done;
		// Resume from the correct spot
		switch (getPublicState().status) {
			case NOT_STARTED:
				getLogic().initializeDraft();
				selectHero();
				break;
			// TODO: Handle other cases for proper resuming
		}
	}

	protected void selectHero() {
		getBehaviour().chooseHeroAsync(getPublicState().heroClassChoices, this::onHeroSelected);
	}

	protected void onHeroSelected(AsyncResult<HeroClass> choice) {
		if (choice.failed()) {
			// TODO: Retry
			return;
		}

		getLogic().startDraft(choice.result());
		selectCard();
	}

	protected void selectCard() {
		getBehaviour().chooseCardAsync(getLogic().getCardChoices(), this::onCardSelected);
	}

	protected void onCardSelected(AsyncResult<Integer> selectedCardResult) {
		getLogic().selectCard(selectedCardResult.result());

		if (getLogic().isDraftOver()) {
			// TODO: What do we do when we're done? We should create a deck and populate it
			handleDone.handle(new Result<>(this));
		} else {
			selectCard();
		}
	}

	public DraftLogic getLogic() {
		return logic;
	}

	public void setLogic(DraftLogic logic) {
		this.logic = logic;
	}

	public PublicDraftState getPublicState() {
		return publicState;
	}

	public void setPublicState(PublicDraftState publicState) {
		this.publicState = publicState;
	}

	public PrivateDraftState getPrivateState() {
		return privateState;
	}

	public void setPrivateState(PrivateDraftState privateState) {
		this.privateState = privateState;
	}

	public DraftBehaviour getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(DraftBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	public DraftContext withLogic(final DraftLogic logic) {
		this.setLogic(logic);
		return this;
	}

	public DraftContext withPublicState(final PublicDraftState publicState) {
		this.setPublicState(publicState);
		return this;
	}

	public DraftContext withPrivateState(final PrivateDraftState privateState) {
		this.setPrivateState(privateState);
		return this;
	}

	public DraftContext withBehaviour(final DraftBehaviour behaviour) {
		this.setBehaviour(behaviour);
		return this;
	}

	protected void notifyPublicStateChanged() {
		getBehaviour().notifyDraftState(getPublicState());
	}
}
