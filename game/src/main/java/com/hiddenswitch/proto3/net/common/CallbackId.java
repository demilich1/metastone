package com.hiddenswitch.proto3.net.common;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by bberman on 12/5/16.
 */
public final class CallbackId {
	public final String id;
	public final int playerId;

	public CallbackId(String id, int playerId) {
		this.id = id;
		this.playerId = playerId;
	}

	public CallbackId(String id) {
		this.id = id;
		this.playerId = -1;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null
				|| !(other instanceof CallbackId)) {
			return false;
		}
		CallbackId rhs = (CallbackId) other;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.toHashCode();
	}

	public static CallbackId of(String id) {
		return new CallbackId(id);
	}
}
