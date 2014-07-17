package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SavageRoar extends SpellCard {

	public SavageRoar() {
		super("Savage Roar", Rarity.FREE, HeroClass.DRUID, 3);
		setDescription("Give your characters +2 Attack this turn.");
		BuffSpell buff = new BuffSpell(+2, 0, new TurnEndTrigger());
		buff.setTarget(EntityReference.FRIENDLY_CHARACTERS);
		setSpell(buff);
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 18;
	}
}
