package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ClearOverloadSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class LavaShock extends SpellCard {

	public LavaShock() {
		super("Lava Shock", Rarity.RARE, HeroClass.SHAMAN, 2);
		setDescription("Deal $2 damage. Unlock your Overloaded Mana Crystals.");
		
		setSpell(MetaSpell.create(DamageSpell.create(2), ClearOverloadSpell.create()));
		setTargetRequirement(TargetSelection.ANY);
	}

}
