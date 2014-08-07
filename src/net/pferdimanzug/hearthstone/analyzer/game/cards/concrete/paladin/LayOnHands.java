package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class LayOnHands extends SpellCard {

	public LayOnHands() {
		super("Lay on Hands", Rarity.EPIC, HeroClass.PALADIN, 8);
		setDescription("Restore 8 Health. Draw 3 cards.");
		
		SpellDesc healSpell = HealingSpell.create(8);
		healSpell.setTarget(EntityReference.FRIENDLY_HERO);
		SpellDesc drawSpell = DrawCardSpell.create(3);
		setSpell(MetaSpell.create(healSpell, drawSpell));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 251;
	}
}
