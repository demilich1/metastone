package net.demilich.metastone.game.spells.desc;

import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.logic.CustomCloneable;
import net.demilich.metastone.game.spells.IValueProvider;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.targeting.EntityReference;

public class SpellDesc extends CustomCloneable {

	private final Map<SpellArg, Object> arguments;

	public static Map<SpellArg, Object> build(Class<? extends Spell> spellClass) {
		final Map<SpellArg, Object> arguments = new EnumMap<>(SpellArg.class);
		arguments.put(SpellArg.SPELL_CLASS, spellClass);
		return arguments;
	}

	public SpellDesc(Map<SpellArg, Object> arguments) {
		this.arguments = arguments;
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
	
	public SpellDesc addArg(SpellArg spellArg, Object value) {
		SpellDesc clone = clone();
		clone.arguments.put(spellArg, value);
		return clone;
	}

	public boolean contains(SpellArg spellArg) {
		return arguments.containsKey(spellArg);
	}

	public Object get(SpellArg spellArg) {
		return arguments.get(spellArg);
	}

	public boolean getBool(SpellArg spellArg) {
		return arguments.containsKey(spellArg) ? (boolean) get(spellArg) : false;
	}

	public int getInt(SpellArg spellArg) {
		return arguments.containsKey(spellArg) ? (int) get(spellArg) : 0;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Spell> getSpellClass() {
		return (Class<? extends Spell>) arguments.get(SpellArg.SPELL_CLASS);
	}

	public EntityReference getTarget() {
		return (EntityReference) arguments.get(SpellArg.TARGET);
	}

	public TargetPlayer getTargetPlayer() {
		return (TargetPlayer) get(SpellArg.TARGET_PLAYER);
	}

	public int getValue() {
		return getInt(SpellArg.VALUE);
	}

	public IValueProvider getValueProvider() {
		return (IValueProvider) get(SpellArg.VALUE_PROVIDER);
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

	public boolean hasPredefinedTarget() {
		return arguments.get(SpellArg.TARGET) != null;
	}

}
