package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ImplosionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Implosion extends SpellCard {

	public Implosion() {
		super("Imp-losion", Rarity.RARE, HeroClass.WARLOCK, 4);
		setDescription("Deal 2-4 damage to a minion. Summon a 1/1 Imp for each damage dealt.");
		
		setSpell(ImplosionSpell.create(2, 4));
		setTargetRequirement(TargetSelection.MINIONS);
	}

}
