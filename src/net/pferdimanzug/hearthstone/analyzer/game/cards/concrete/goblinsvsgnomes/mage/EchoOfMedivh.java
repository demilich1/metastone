package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.PutCopyInHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class EchoOfMedivh extends SpellCard {

	public EchoOfMedivh() {
		super("Echo of Medivh", Rarity.EPIC, HeroClass.MAGE, 4);
		setDescription("Put a copy of each friendly minion into your hand.");
		
		setSpell(PutCopyInHandSpell.create());
		setPredefinedTarget(EntityReference.FRIENDLY_MINIONS);
		setTargetRequirement(TargetSelection.NONE);
	}

}
