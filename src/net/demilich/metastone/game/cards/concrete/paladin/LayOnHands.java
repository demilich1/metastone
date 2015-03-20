package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class LayOnHands extends SpellCard {

	public LayOnHands() {
		super("Lay on Hands", Rarity.EPIC, HeroClass.PALADIN, 8);
		setDescription("Restore 8 Health. Draw 3 cards.");
		
		SpellDesc healSpell = HealingSpell.create(EntityReference.FRIENDLY_HERO, 8);
		SpellDesc drawSpell = DrawCardSpell.create(3);
		setSpell(MetaSpell.create(healSpell, drawSpell));
		setTargetRequirement(TargetSelection.ANY);
	}

	@Override
	public int getTypeId() {
		return 251;
	}
}
