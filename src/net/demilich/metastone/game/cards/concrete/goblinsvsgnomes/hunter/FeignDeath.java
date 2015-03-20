package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.TriggerDeathrattleSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class FeignDeath extends SpellCard {

	public FeignDeath() {
		super("Feign Death", Rarity.EPIC, HeroClass.HUNTER, 2);
		setDescription("Trigger all Deathrattles on your minions.");

		SpellDesc triggerDeathrattles = TriggerDeathrattleSpell.create(EntityReference.FRIENDLY_MINIONS);
		setSpell(triggerDeathrattles);

		setTargetRequirement(TargetSelection.NONE);
	}


	@Override
	public int getTypeId() {
		return 486;
	}
}
