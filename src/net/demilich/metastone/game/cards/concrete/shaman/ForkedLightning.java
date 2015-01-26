package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.MultiTargetDamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ForkedLightning extends SpellCard {

	public ForkedLightning() {
		super("Forked Lightning", Rarity.COMMON, HeroClass.SHAMAN, 1);
		setDescription("Deal 2 damage to 2 random enemy minions. Overload: (2)");
		setTag(GameTag.OVERLOAD, 2);
		setSpell(MultiTargetDamageSpell.create(2, 2));
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public boolean canBeCast(GameContext context, Player player) {
		return context.getOpponent(player).getMinions().size() >= 2;
	}

	@Override
	public int getTypeId() {
		return 321;
	}
}
