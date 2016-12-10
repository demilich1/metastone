package com.hiddenswitch.proto3.net.impl.util;

import net.demilich.metastone.game.decks.Deck;
import org.joda.time.DateTimeComparator;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * Created by bberman on 11/30/16.
 */
public class QueueEntry implements Comparable<QueueEntry> {
	public final String userId;
	public final Deck deck;
	public final Date lastTouchedAt;
	public final long createdAt;

	public QueueEntry(String userId, Deck deck) {
		this.createdAt = System.nanoTime();
		this.userId = userId;
		this.deck = deck;
		this.lastTouchedAt = Date.from(Instant.now());
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof String) {
			return this.userId.equals((String) other);
		}

		return Objects.equals(((QueueEntry) other).userId, this.userId);
	}

	@Override
	public int compareTo(QueueEntry o) {
		return DateTimeComparator.getInstance().compare(this.lastTouchedAt, o.lastTouchedAt);
	}
}
