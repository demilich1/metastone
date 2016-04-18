package net.demilich.metastone.game.spells.desc.valueprovider;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.PlayerAttribute;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.statistics.Statistic;

public class PlayerAttributeValueProvider extends ValueProvider {

	public PlayerAttributeValueProvider(ValueProviderDesc desc) {
		super(desc);
	}

	@Override
	protected int provideValue(GameContext context, Player player, Entity target, Entity host) {
		PlayerAttribute attribute = (PlayerAttribute) desc.get(ValueProviderArg.PLAYER_ATTRIBUTE);
		switch (attribute) {
		case DECK_COUNT:
			return player.getDeck().getCount();
		case HAND_COUNT:
			return player.getHand().getCount();
		case HERO_POWER_USED:
			return (int) player.getStatistics().getLong(Statistic.HERO_POWER_USED);
		case MANA:
			return player.getMana();
		case MAX_MANA:
			return player.getMaxMana();
		case SECRET_COUNT:
			return player.getSecrets().size();
		case SPELLS_CAST:
			return (int) player.getStatistics().getLong(Statistic.SPELLS_CAST);
		default:
			break;
		}
		return 0;
	}

}
