package net.demilich.metastone.game.spells.desc.aura;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.cards.desc.Desc;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.aura.Aura;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class AuraDesc extends Desc<AuraArg> {

	public static Map<AuraArg, Object> build(Class<? extends Aura> auraClass) {
		final Map<AuraArg, Object> arguments = new EnumMap<>(AuraArg.class);
		arguments.put(AuraArg.CLASS, auraClass);
		return arguments;
	}

	public AuraDesc(Map<AuraArg, Object> arguments) {
		super(arguments);
	}

	public Aura create() {
		Class<? extends Aura> auraClass = getAuraClass();
		try {
			return auraClass.getConstructor(AuraDesc.class).newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SpellDesc getApplyEffect() {
		return (SpellDesc) get(AuraArg.APPLY_EFFECT);
	}

	public Attribute getAttribute() {
		return (Attribute) get(AuraArg.ATTRIBUTE);
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Aura> getAuraClass() {
		return (Class<? extends Aura>) get(AuraArg.CLASS);
	}

	public Race getRaceRestriction() {
		return (Race) get(AuraArg.RACE_RESTRICTION);
	}

	public SpellDesc getRemoveEffect() {
		return (SpellDesc) get(AuraArg.REMOVE_EFFECT);
	}

	public EntityReference getTarget() {
		return (EntityReference) get(AuraArg.TARGET);
	}

}
