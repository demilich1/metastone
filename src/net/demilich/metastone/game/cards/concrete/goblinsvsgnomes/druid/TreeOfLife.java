package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.HealToFullSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class TreeOfLife extends SpellCard {

	public TreeOfLife() {
		super("Tree of Life", Rarity.EPIC, HeroClass.DRUID, 9);
		setDescription("Restore all characters to full Health.");

		setSpell(HealToFullSpell.create());
		setPredefinedTarget(EntityReference.ALL_CHARACTERS);
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 483;
	}
}
