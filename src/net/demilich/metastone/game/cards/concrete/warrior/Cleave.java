package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.MultiTargetDamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Cleave extends SpellCard {

	public Cleave() {
		super("Cleave", Rarity.FREE, HeroClass.WARRIOR, 2);
		setDescription("Deal $2 damage to two random enemy minions.");
		setSpell(MultiTargetDamageSpell.create(2, 2));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public boolean canBeCast(GameContext context, Player player) {
		return context.getOpponent(player).getMinions().size() >= 2;
	}
	
	



	@Override
	public int getTypeId() {
		return 365;
	}
}
