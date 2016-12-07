package com.hiddenswitch.proto3.net.util;

import com.hiddenswitch.proto3.net.models.QueueEntry;
import net.demilich.metastone.game.decks.Deck;
import org.apache.commons.collections.list.TreeList;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTimeComparator;

import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by bberman on 11/30/16.
 */
public class Matchmaker extends AbstractMap<String, QueueEntry> {
	private final int timeoutSeconds;
	private Map<String, QueueEntry> entries = new HashMap<>();
	private Map<String, Match> matches = new HashMap<>();
	private TreeList queue = new TreeList();

	public Matchmaker() {
		timeoutSeconds = 4;
	}

	public class Match {
		public final String gameId;
		public final QueueEntry entry1;
		public final QueueEntry entry2;
		public final Date createdAt;

		public Match(QueueEntry entry1, QueueEntry entry2) {
			this.gameId = RandomStringUtils.randomAlphanumeric(timeoutSeconds).toLowerCase();
			this.entry1 = entry1;
			this.entry2 = entry2;
			this.createdAt = Date.from(Instant.now());
			matches.put(this.entry1.userId, this);
			matches.put(this.entry2.userId, this);
		}
	}

	public synchronized boolean expire(String gameId) {
		if (!matches.containsKey(gameId)) {
			return false;
		}
		Match match = matches.get(gameId);
		matches.remove(gameId);
		remove(match.entry1.userId);
		remove(match.entry2.userId);
		return true;
	}

	public synchronized Match match(String userId, Deck deck) {
		if (matches.containsKey(userId)) {
			return matches.get(userId);
		}

		if (!contains(userId)) {
			if (deck == null) {
				throw new ArrayStoreException();
			}
			this.asQueue().add(new QueueEntry(userId, deck));
		} else {
			QueueEntry entry = this.get(userId);
			QueueEntry replacement = new QueueEntry(userId, entry.deck);
			this.replace(userId, replacement);
		}

		QueueEntry otherPlayer = asQueue().peekSecond();

		while (otherPlayer != null) {
			// Skip really old players
			if (otherPlayer.lastTouchedAt.before(Date.from(Instant.now().minusSeconds(timeoutSeconds)))) {
				this.remove(otherPlayer);
				otherPlayer = asQueue().peekSecond();
			} else {
				return new Match(asQueue().poll(), asQueue().poll());
			}
		}

		return null;
	}

	private MatchmakingQueue asQueue() {
		return new MatchmakingQueue();
	}

	@SuppressWarnings("unchecked")
	private Comparator getComparator() {
		return (o1, o2) -> DateTimeComparator.getInstance().compare(((QueueEntry) o1).lastTouchedAt, ((QueueEntry) o2).lastTouchedAt);
	}

	@Override
	public Set<Entry<String, QueueEntry>> entrySet() {
		return entries.entrySet();
	}

	@Override
	@SuppressWarnings("unchecked")
	public synchronized QueueEntry replace(String key, QueueEntry value) {
		int oldQueue = queue.indexOf(key);
		queue.remove(oldQueue);
		queue.add(value);

		sortAfterAdd();

		entries.put(key, value);
		return value;
	}

	public boolean contains(Object o) {
		if (o instanceof String) {
			return contains((String) o);
		} else if (o instanceof QueueEntry) {
			return contains((QueueEntry) o);
		} else {
			return false;
		}
	}

	public boolean contains(String userId) {
		return entries.containsKey(userId);
	}

	public boolean contains(QueueEntry entry) {
		return entries.containsKey(entry.userId);
	}


	@SuppressWarnings({"unchecked"})
	protected synchronized void sortAfterAdd() {
		if (queue.size() >= 2) {
			QueueEntry newLast = (QueueEntry) queue.get(queue.size() - 1);
			QueueEntry formerLast = (QueueEntry) queue.get(queue.size() - 2);
			if (formerLast.compareTo(newLast) > 0) {
				queue.sort(getComparator());
			}
		}
	}

	@Override
	public synchronized QueueEntry remove(Object key) {
		QueueEntry entry = entries.get(key);
		remove((String) key);
		return entry;
	}

	public synchronized boolean remove(String userId) {
		QueueEntry entry = entries.get(userId);
		entries.remove(userId);
		if (entry != null) {
			queue.remove(entry);
		}
		return true;
	}

	public synchronized boolean remove(QueueEntry entry) {
		entries.remove(entry.userId);
		queue.remove(entry);
		return true;
	}

	private class MatchmakingQueue implements Queue<QueueEntry> {
		@Override
		public int size() {
			return queue.size();
		}

		@Override
		public boolean isEmpty() {
			return queue.isEmpty();
		}

		@Override
		public boolean contains(Object o) {
			return Matchmaker.this.contains(o);
		}

		@Override
		public Iterator<QueueEntry> iterator() {
			return new Iterator<QueueEntry>() {
				private Iterator iterator = queue.iterator();

				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}

				@Override
				public QueueEntry next() {
					return (QueueEntry) iterator.next();
				}
			};
		}

		@Override
		@SuppressWarnings({"rawTypes", "unchecked"})
		public Object[] toArray() {
			return queue.toArray();
		}

		@Override
		@SuppressWarnings({"rawTypes", "unchecked"})
		public <T> T[] toArray(T[] a) {
			throw new ArrayStoreException();
		}


		@Override
		@SuppressWarnings({"rawTypes", "unchecked"})
		public synchronized boolean add(QueueEntry queueEntry) {
			queue.add(queueEntry);

			sortAfterAdd();

			entries.put(queueEntry.userId, queueEntry);
			return true;
		}

		@Override
		public boolean remove(Object e) {
			if (e instanceof String) {
				return Matchmaker.this.remove((String) e);
			} else if (e instanceof QueueEntry) {
				return Matchmaker.this.remove((QueueEntry) e);
			}
			return false;
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return entries.entrySet().containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends QueueEntry> c) {
			for (QueueEntry e : c) {
				this.add(e);
			}
			return true;
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			for (Object e : c) {
				this.remove(e);
			}
			return true;
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return entries.keySet().retainAll(c);
		}

		@Override
		public void clear() {
			entries.clear();
			queue.clear();
		}

		@Override
		public boolean offer(QueueEntry queueEntry) {
			return this.add(queueEntry);
		}

		@Override
		public synchronized QueueEntry poll() {
			int lastIndex = queue.size() - 1;
			if (lastIndex < 0) {
				return null;
			}
			QueueEntry last = (QueueEntry) queue.get(lastIndex);
			queue.remove(lastIndex);
			entries.remove(last.userId);
			return last;
		}

		@Override
		public QueueEntry element() {
			int lastIndex = queue.size() - 1;
			if (lastIndex < 0) {
				throw new NoSuchElementException();
			}
			return (QueueEntry) queue.get(lastIndex);
		}

		@Override
		public QueueEntry peek() {
			int lastIndex = queue.size() - 1;
			if (lastIndex < 0) {
				return null;
			}
			return (QueueEntry) queue.get(lastIndex);
		}

		public QueueEntry peekSecond() {
			int lastIndex = queue.size() - 2;
			if (lastIndex < 0) {
				return null;
			}
			return (QueueEntry) queue.get(lastIndex);
		}

		@Override
		public synchronized QueueEntry remove() {
			int lastIndex = queue.size() - 1;
			if (lastIndex < 0) {
				throw new NoSuchElementException();
			}
			QueueEntry last = (QueueEntry) queue.get(lastIndex);
			queue.remove(lastIndex);
			entries.remove(last.userId);
			return last;
		}
	}
}
