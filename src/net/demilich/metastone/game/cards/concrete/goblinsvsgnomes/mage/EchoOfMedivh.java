package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.PutCopyInHandSpell;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class EchoOfMedivh extends SpellCard {

	public EchoOfMedivh() {
		super("Echo of Medivh", Rarity.EPIC, HeroClass.MAGE, 4);
		setDescription("Put a copy of each friendly minion into your hand.");
		
		setSpell(PutCopyInHandSpell.create());
		setPredefinedTarget(EntityReference.FRIENDLY_MINIONS);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 492;
	}
}
