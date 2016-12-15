package com.hiddenswitch.proto3.draft;

import com.hiddenswitch.proto3.net.common.Recursive;
import com.hiddenswitch.proto3.net.util.Result;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by bberman on 12/14/16.
 */
public class DraftContext {
	private DraftLogic logic = new DraftLogic(this);
	private PublicDraftState publicState = new PublicDraftState();
	private PrivateDraftState privateState = new PrivateDraftState();
	private DraftBehaviour behaviour = new NullDraftBehaviour();

	public DraftContext() {
	}

	void run(Handler<AsyncResult<DraftContext>> handler) {
		// Resume from the correct spot
		switch (getPublicState().status) {
			case NOT_STARTED:
				getLogic().initializeDraft();
				getBehaviour().chooseHeroAsync(getPublicState().heroClassChoices, choice -> {
					if (choice.failed()) {
						// TODO: Retry
						return;
					}

					getLogic().startDraft(choice.result());
					Recursive<Runnable> selectCardLoop = new Recursive<>();
					selectCardLoop.func = () -> {
						getBehaviour().chooseCardAsync(getLogic().getCardChoices(), selectedCardResult -> {
							getLogic().selectCard(selectedCardResult.result());

							if (getLogic().isDraftOver()) {
								// TODO: What do we do when we're done? We should create a deck and populate it
								handler.handle(new Result<>(this));
							} else {
								selectCardLoop.func.run();
							}
						});
					};
					selectCardLoop.func.run();
				});
				break;
				// TODO: Handle other cases for proper resuming
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
}
