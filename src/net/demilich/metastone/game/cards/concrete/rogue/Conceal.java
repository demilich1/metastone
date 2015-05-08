package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Conceal extends SpellCard {

	public Conceal() {
		super("Conceal", Rarity.COMMON, HeroClass.ROGUE, 1);
		setDescription("Give your minions Stealth until your next turn.");
		SpellDesc stealth = AddAttributeSpell.create(EntityReference.FRIENDLY_MINIONS, GameTag.STEALTH, new TurnStartTrigger(null));
		setSpell(stealth);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 290;
	}
}
