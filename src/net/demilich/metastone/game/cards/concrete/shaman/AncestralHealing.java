package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.HealToFullSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class AncestralHealing extends SpellCard {

	public AncestralHealing() {
		super("Ancestral Healing", Rarity.FREE, HeroClass.SHAMAN, 0);
		setDescription("Restore a minion to full Health and give it Taunt.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(MetaSpell.create(HealToFullSpell.create(), AddAttributeSpell.create(GameTag.TAUNT)));
	}

	@Override
	public int getTypeId() {
		return 310;
	}
}
