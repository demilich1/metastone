package net.demilich.metastone.game.spells;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellArg;
import net.demilich.metastone.game.spells.desc.SpellDesc;
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

	private static SpellDesc create(IValueProvider drawModifier, int numberOfCards, TargetPlayer targetPlayer) {
		SpellDesc desc = new SpellDesc(DrawCardSpell.class);
		desc.set(SpellArg.VALUE_PROVIDER, drawModifier);
		desc.set(SpellArg.NUMBER_OF_CARDS, numberOfCards);
		desc.setTargetPlayer(targetPlayer);
		desc.setTarget(EntityReference.NONE);
		return desc;
	}

	public static SpellDesc create(IValueProvider drawModifier, TargetPlayer targetPlayer) {
		return create(drawModifier, 0, targetPlayer);
	}

	private void draw(GameContext context, Player player, int numberOfCards, IValueProvider drawModifier) {
		int cardCount = drawModifier != null ? drawModifier.provideValue(context, player, null) : numberOfCards;
		for (int i = 0; i < cardCount; i++) {
			context.getLogic().drawCard(player.getId());
		}
	}

	@Override
	protected void onCast(GameContext context, Player player, SpellDesc desc, Entity target) {
		int numberOfCards = desc.getInt(SpellArg.NUMBER_OF_CARDS);
		TargetPlayer targetPlayer = desc.getTargetPlayer();
		IValueProvider drawModifier = desc.getValueProvider();
		Player opponent = context.getOpponent(player);
		switch (targetPlayer) {
		case BOTH:
			draw(context, player, numberOfCards, drawModifier);
			draw(context, opponent, numberOfCards, drawModifier);
			break;
		case OPPONENT:
			draw(context, opponent, numberOfCards, drawModifier);
			break;
		case SELF:
			draw(context, player, numberOfCards, drawModifier);
			break;
		default:
			break;
		}
	}
}
