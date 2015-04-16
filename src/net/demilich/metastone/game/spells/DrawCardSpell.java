package net.demilich.metastone.game.spells;

import java.util.Map;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.EntityReference;

public class DrawCardSpell extends Spell {

	public static SpellDesc create() {
		return create(1);
	}

	public static SpellDesc create(int numberOfCards) {
		return create(numberOfCards, TargetPlayer.SELF);
	}

	public static SpellDesc create(int numberOfCards, TargetPlayer targetPlayer) {
		return create(null, numberOfCards, targetPlayer);
	}

	private static SpellDesc create(ValueProvider drawModifier, int numberOfCards, TargetPlayer targetPlayer) {
		Map<SpellArg, Object> arguments = SpellDesc.build(DrawCardSpell.class);
		arguments.put(SpellArg.VALUE_PROVIDER, drawModifier);
		arguments.put(SpellArg.VALUE, numberOfCards);
		arguments.put(SpellArg.TARGET, EntityReference.NONE);
		arguments.put(SpellArg.TARGET_PLAYER, targetPlayer);
		return new SpellDesc(arguments);
	}

	public static SpellDesc create(ValueProvider drawModifier, TargetPlayer targetPlayer) {
		return create(drawModifier, 0, targetPlayer);
	}

	private void draw(GameContext context, Player player, int numberOfCards, ValueProvider drawModifier) {
		int cardCount = drawModifier != null ? drawModifier.getValue(context, player, null) : numberOfCards;
		for (int i = 0; i < cardCount; i++) {
			context.getLogic().drawCard(player.getId());
		}
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity source, Entity target) {
		int numberOfCards = desc.getInt(SpellArg.VALUE);
		ValueProvider drawModifier = desc.getValueProvider();
		draw(context, player, numberOfCards, drawModifier);
	}
}
