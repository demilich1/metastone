package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Demonwrath extends SpellCard {

	public Demonwrath() {
		super("Demonwrath", Rarity.RARE, HeroClass.WARLOCK, 3);
		setDescription("Deal 2 damage to all non-Demon minions.");

		SpellDesc spell = DamageSpell.create(EntityReference.ALL_MINIONS, 2, entity -> entity.getTag(GameTag.RACE) != Race.DEMON, false);
		setSpell(spell);
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 628;
	}
}
