package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class FrostNova extends SpellCard {

	public FrostNova() {
		super("Frost Nova", Rarity.FREE, HeroClass.MAGE, 3);
		setDescription("Freeze all enemy minions.");
		setSpell(ApplyTagSpell.create(GameTag.FROZEN));
		setTargetRequirement(TargetSelection.NONE);
		setPredefinedTarget(EntityReference.ENEMY_MINIONS);
	}

	@Override
	public int getTypeId() {
		return 62;
	}
}
