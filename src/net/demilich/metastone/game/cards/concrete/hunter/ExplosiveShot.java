package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.custom.AdjacentMetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ExplosiveShot extends SpellCard {

	public ExplosiveShot() {
		super("Explosive Shot", Rarity.RARE, HeroClass.HUNTER, 5);
		setDescription("Deal $5 damage to a minion and $2 damage to adjacent ones.");
		SpellDesc primary = DamageSpell.create(5);
		SpellDesc secondary = DamageSpell.create(2);
		setSpell(AdjacentMetaSpell.create(primary, secondary));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 31;
	}

	
}
