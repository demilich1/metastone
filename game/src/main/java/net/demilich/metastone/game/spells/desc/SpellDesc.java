package net.demilich.metastone.game.spells.desc;

import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.cards.desc.Desc;
import net.demilich.metastone.game.logic.CustomCloneable;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.targeting.EntityReference;

public class SpellDesc extends Desc<SpellArg> {

	public SpellDesc(Map<SpellArg, Object> arguments) {
		super(arguments);
	}

	public static Map<SpellArg, Object> build(Class<? extends Spell> spellClass) {
		final Map<SpellArg, Object> arguments = new EnumMap<>(SpellArg.class);
		arguments.put(SpellArg.CLASS, spellClass);
		return arguments;
	}

	public SpellDesc addArg(SpellArg spellArg, Object value) {
		SpellDesc clone = clone();
		clone.arguments.put(spellArg, value);
		return clone;
	}
	
	public SpellDesc removeArg(SpellArg spellArg) {
		SpellDesc clone = clone();
		clone.arguments.remove(spellArg);
		return clone;
	}

	@Override
	public SpellDesc clone() {
		SpellDesc clone = new SpellDesc(build(getSpellClass()));
		for (SpellArg spellArg : arguments.keySet()) {
			Object value = arguments.get(spellArg);
			if (value instanceof CustomCloneable) {
				CustomCloneable cloneable = (CustomCloneable) value;
				clone.arguments.put(spellArg, cloneable.clone());
			} else {
				clone.arguments.put(spellArg, value);
			}
		}
		return clone;
	}

	public EntityFilter getEntityFilter() {
		return (EntityFilter) get(SpellArg.FILTER);
	}

	public int getInt(SpellArg spellArg, int defaultValue) {
		return arguments.containsKey(spellArg) ? (int) get(spellArg) : defaultValue;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Spell> getSpellClass() {
		return (Class<? extends Spell>) arguments.get(SpellArg.CLASS);
	}

	public EntityReference getTarget() {
		return (EntityReference) arguments.get(SpellArg.TARGET);
	}

	public TargetPlayer getTargetPlayer() {
		return (TargetPlayer) get(SpellArg.TARGET_PLAYER);
	}

	public boolean hasPredefinedTarget() {
		return arguments.get(SpellArg.TARGET) != null;
	}

	@Override
	public String toString() {
		String result = "[SpellDesc arguments= {\n";
		for (SpellArg spellArg : arguments.keySet()) {
			result += "\t" + spellArg + ": " + arguments.get(spellArg) + "\n";
		}
		result += "}";
		return result;
	}

}
