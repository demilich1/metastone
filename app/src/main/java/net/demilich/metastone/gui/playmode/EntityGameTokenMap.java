package net.demilich.metastone.gui.playmode;

import net.demilich.metastone.game.entities.Entity;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by bberman on 12/5/16.
 */
class EntityGameTokenMap implements Map<Entity, GameToken> {
	private final ArrayList<Entry<Entity, GameToken>> list = new ArrayList<>();

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		Entity k = (Entity) key;
		return list.stream().anyMatch(e -> e.getKey().getId() == k.getId());
	}

	@Override
	public boolean containsValue(Object value) {
		GameToken v = (GameToken) value;
		return list.stream().anyMatch(e -> e.getValue().equals(v));
	}

	@Override
	public GameToken get(Object key) {
		if (key == null) {
			return null;
		}
		Entity k = (Entity) key;
		final Optional<Entry<Entity, GameToken>> first = list.stream().filter(e -> e.getKey().getId() == k.getId()).findFirst();
		if (first.isPresent()) {
			return first.get().getValue();
		} else {
			return null;
		}
	}

	@Override
	public GameToken put(Entity key, GameToken value) {
		list.add(new AbstractMap.SimpleEntry<>(key, value));
		return value;
	}

	@Override
	public GameToken remove(Object key) {
		Entity k = (Entity) key;
		list.removeIf(e -> e.getKey().getId() == k.getId());
		return null;
	}

	@Override
	public void putAll(Map<? extends Entity, ? extends GameToken> m) {
		list.addAll(m.entrySet().stream().map(e -> new AbstractMap.SimpleEntry<Entity, GameToken>(e.getKey(), e.getValue())).collect(Collectors.toSet()));
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public Set<Entity> keySet() {
		return list.stream().map(Entry::getKey).collect(Collectors.toSet());
	}

	@Override
	public Collection<GameToken> values() {
		return list.stream().map(Entry::getValue).collect(Collectors.toSet());
	}

	@Override
	public Set<Entry<Entity, GameToken>> entrySet() {
		return list.stream().collect(Collectors.toSet());
	}
}
