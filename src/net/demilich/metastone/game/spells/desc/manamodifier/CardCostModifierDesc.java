package net.demilich.metastone.game.spells.desc.manamodifier;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;

import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.cards.desc.Desc;

public class CardCostModifierDesc extends Desc<CardCostModifierArg> {

	public static Map<CardCostModifierArg, Object> build(Class<? extends CardCostModifier> manaModifierClass) {
		final Map<CardCostModifierArg, Object> arguments = new EnumMap<>(CardCostModifierArg.class);
		arguments.put(CardCostModifierArg.CLASS, manaModifierClass);
		return arguments;
	}


	public CardCostModifierDesc(Map<CardCostModifierArg, Object> arguments) {
		super(arguments);
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
	

}
