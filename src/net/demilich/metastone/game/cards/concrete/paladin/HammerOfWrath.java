package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HammerOfWrath extends SpellCard {

	public HammerOfWrath() {
		super("Hammer of Wrath", Rarity.FREE, HeroClass.PALADIN, 4);
		setDescription("Deal $3 damage. Draw a card.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(MetaSpell.create(DamageSpell.create(3), DrawCardSpell.create()));
	}
	



	@Override
	public int getTypeId() {
		return 246;
	}
}
