package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.InnerFireSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class InnerFire extends SpellCard {

	public InnerFire() {
		super("Inner Fire", Rarity.COMMON, HeroClass.PRIEST, 1);
		setDescription("Change a minion's Attack to be equal to its Health.");
		setSpell(InnerFireSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	@Override
	public int getTypeId() {
		return 266;
	}
	
}
