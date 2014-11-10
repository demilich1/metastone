package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Flamecannon extends SpellCard {

	public Flamecannon() {
		super("Flamecannon", Rarity.COMMON, HeroClass.MAGE, 2);
		setDescription("Deal 4 damage to a random enemy minion.");
		
		SpellDesc spellDesc = DamageSpell.create(4);
		spellDesc.pickRandomTarget(true);
		spellDesc.setTarget(EntityReference.ENEMY_MINIONS);
		setSpell(spellDesc);
		
		setTargetRequirement(TargetSelection.NONE);
	}

}
