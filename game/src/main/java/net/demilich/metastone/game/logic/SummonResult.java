package net.demilich.metastone.game.logic;

import io.vertx.core.AsyncResult;

import java.io.Serializable;

/**
 * Created by bberman on 11/27/16.
 */
public class SummonResult implements AsyncResult<Boolean>, Serializable {
	public final static SummonResult SUMMONED = new SummonResult(true);
	public final static SummonResult NOT_SUMMONED = new SummonResult(false);
	private boolean summoned;

	public SummonResult(boolean summoned) {
		this.summoned = summoned;
	}

	@Override
	public Boolean result() {
		return summoned;
	}

	@Override
	public Throwable cause() {
		return null;
	}

	@Override
	public boolean succeeded() {
		return true;
	}

	@Override
	public boolean failed() {
		return false;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null
				|| !(other instanceof SummonResult)) {
			return false;
		}

		return this.summoned == ((SummonResult) other).summoned;
	}
}
