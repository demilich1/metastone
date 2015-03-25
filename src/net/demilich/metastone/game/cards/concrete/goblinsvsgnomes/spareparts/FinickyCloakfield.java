package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.trigger.TurnStartTrigger;
import net.demilich.metastone.game.targeting.TargetSelection;

public class FinickyCloakfield extends SpellCard {

	public FinickyCloakfield() {
		super("Finicky Cloakfield", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Give a friendly minion Stealth until your next turn.");
		
		setSpell(AddAttributeSpell.create(GameTag.STEALTH, new TurnStartTrigger()));
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		
		setCollectible(false);
	}



	@Override
	public int getTypeId() {
		return 585;
	}
}
