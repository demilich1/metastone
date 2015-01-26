package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.ImplosionSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Implosion extends SpellCard {

	public Implosion() {
		super("Imp-losion", Rarity.RARE, HeroClass.WARLOCK, 4);
		setDescription("Deal 2-4 damage to a minion. Summon a 1/1 Imp for each damage dealt.");
		
		setSpell(ImplosionSpell.create(2, 4));
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 600;
	}
}
