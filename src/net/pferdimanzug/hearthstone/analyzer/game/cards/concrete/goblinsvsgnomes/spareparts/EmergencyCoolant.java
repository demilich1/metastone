package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class EmergencyCoolant extends SpellCard {

	public EmergencyCoolant() {
		super("Emergency Coolant", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Freeze a minion.");
		
		setSpell(ApplyTagSpell.create(GameTag.FROZEN, new TurnStartTrigger()));
		setTargetRequirement(TargetSelection.MINIONS);
		
		setCollectible(false);
	}



	@Override
	public int getTypeId() {
		return 584;
	}
}
