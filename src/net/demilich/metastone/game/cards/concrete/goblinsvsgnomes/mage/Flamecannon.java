package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

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



	@Override
	public int getTypeId() {
		return 493;
	}
}
