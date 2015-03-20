package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DeadlyShot extends SpellCard {

	public DeadlyShot() {
		super("Deadly Shot", Rarity.COMMON, HeroClass.HUNTER, 3);
		setDescription("Destroy a random enemy minion.");
		SpellDesc destroyRandom = DestroySpell.create(EntityReference.ENEMY_MINIONS, true);
		setSpell(destroyRandom);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		if (!super.canBeCast(context, player)) {
			return false;
		}
		Player opponent = context.getOpponent(player);
		return opponent.getMinions().size() > 0;
	}

	@Override
	public int getTypeId() {
		return 29;
	}
}
