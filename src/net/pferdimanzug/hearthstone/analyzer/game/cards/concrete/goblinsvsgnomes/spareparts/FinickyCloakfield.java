package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnStartTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FinickyCloakfield extends SpellCard {

	public FinickyCloakfield() {
		super("Finicky Cloakfield", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Give a friendly minion Stealth until your next turn.");
		
		setSpell(ApplyTagSpell.create(GameTag.STEALTHED, new TurnStartTrigger()));
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		
		setCollectible(false);
	}



	@Override
	public int getTypeId() {
		return 585;
	}
}
