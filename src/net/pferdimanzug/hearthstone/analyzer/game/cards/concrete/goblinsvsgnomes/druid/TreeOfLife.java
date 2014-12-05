package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealToFullSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
