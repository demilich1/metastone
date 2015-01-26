package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.MultiTargetDamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MultiShot extends SpellCard {

	public MultiShot() {
		super("Multi-Shot", Rarity.FREE, HeroClass.HUNTER, 4);
		setDescription("Deal $3 damage to two random enemy minions.");
		setSpell(MultiTargetDamageSpell.create(3, 2));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		if (!super.canBeCast(context, player)) {
			return false;
		}
		return context.getOpponent(player).getMinions().size() >= 2;
	}

	@Override
	public int getTypeId() {
		return 41;
	}
}
