package net.demilich.metastone.game.spells.desc.manamodifier;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.cards.desc.Desc;
import net.demilich.metastone.game.logic.CustomCloneable;

public class CardCostModifierDesc extends Desc<CardCostModifierArg> {

	public static Map<CardCostModifierArg, Object> build(Class<? extends CardCostModifier> manaModifierClass) {
		final Map<CardCostModifierArg, Object> arguments = new EnumMap<>(CardCostModifierArg.class);
		arguments.put(CardCostModifierArg.CLASS, manaModifierClass);
		return arguments;
	}

	public CardCostModifierDesc(Map<CardCostModifierArg, Object> arguments) {
		super(arguments);
	}
	
	public CardCostModifierDesc addArg(CardCostModifierArg cardCostModififerArg, Object value) {
		CardCostModifierDesc clone = clone();
		clone.arguments.put(cardCostModififerArg, value);
		return clone;
	}
	
	public CardCostModifierDesc removeArg(CardCostModifierArg cardCostModififerArg) {
		CardCostModifierDesc clone = clone();
		clone.arguments.remove(cardCostModififerArg);
		return clone;
	}
	
	@Override
	public CardCostModifierDesc clone() {
		CardCostModifierDesc clone = new CardCostModifierDesc(build(getManaModifierClass()));
		for (CardCostModifierArg cardCostModififerArg : arguments.keySet()) {
			Object value = arguments.get(cardCostModififerArg);
			if (value instanceof CustomCloneable) {
				CustomCloneable cloneable = (CustomCloneable) value;
				clone.arguments.put(cardCostModififerArg, cloneable.clone());
			} else {
				clone.arguments.put(cardCostModififerArg, value);
			}
		}
		return clone;
	}

	public CardCostModifier create() {
		Class<? extends CardCostModifier> manaModifierClass = getManaModifierClass();
		try {
			return manaModifierClass.getConstructor(CardCostModifierDesc.class).newInstance(this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends CardCostModifier> getManaModifierClass() {
		return (Class<? extends CardCostModifier>) get(CardCostModifierArg.CLASS);
	}

	@Override
	public String toString() {
		String result = "[CardCostModifierDesc arguments= {\n";
		for (CardCostModifierArg cardCostModififerArg : arguments.keySet()) {
			result += "\t" + cardCostModififerArg + ": " + arguments.get(cardCostModififerArg) + "\n";
		}
		result += "}";
		return result;
	}

}
