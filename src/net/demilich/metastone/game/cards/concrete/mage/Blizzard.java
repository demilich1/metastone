package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Blizzard extends SpellCard {

	public Blizzard() {
		super("Blizzard", Rarity.RARE, HeroClass.MAGE, 6);
		setDescription("Deal $2 damage to all enemy minions and Freeze them.");

		SpellDesc blizzardSpell = MetaSpell.create(EntityReference.ENEMY_MINIONS, DamageSpell.create(2), ApplyTagSpell.create(GameTag.FROZEN));
		setSpell(blizzardSpell);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 55;
	}
}
