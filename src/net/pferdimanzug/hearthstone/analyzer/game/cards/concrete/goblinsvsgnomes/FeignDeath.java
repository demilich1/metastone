package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TriggerDeathrattleSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FeignDeath extends SpellCard {

	public FeignDeath() {
		super("Feign Death", Rarity.EPIC, HeroClass.HUNTER, 2);
		setDescription("Trigger all Deathrattles on your minions.");

		SpellDesc triggerDeathrattles = TriggerDeathrattleSpell.create();
		triggerDeathrattles.setTarget(EntityReference.FRIENDLY_MINIONS);
		setSpell(triggerDeathrattles);

		setTargetRequirement(TargetSelection.NONE);
	}

}
