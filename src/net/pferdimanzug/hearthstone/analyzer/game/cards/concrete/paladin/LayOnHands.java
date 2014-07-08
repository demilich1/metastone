package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class LayOnHands extends SpellCard {

	public LayOnHands() {
		super("Lay on Hands", Rarity.EPIC, HeroClass.PALADIN, 8);
		setDescription("Restore 8 Health. Draw 3 cards.");
		
		Spell healSpell = new HealingSpell(8);
		healSpell.setTarget(EntityReference.FRIENDLY_HERO);
		Spell drawSpell = new DrawCardSpell(3);
		setSpell(new MetaSpell(healSpell, drawSpell));
		setTargetRequirement(TargetSelection.ANY);
	}

}
