package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class FrostShock extends SpellCard {

	public FrostShock() {
		super("Frost Shock", Rarity.FREE, HeroClass.SHAMAN, 1);
		setDescription("Deal $1 damage to an enemy character and Freeze it.");
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
		setSpell(MetaSpell.create(DamageSpell.create(1), AddAttributeSpell.create(GameTag.FROZEN)));
	}

	@Override
	public int getTypeId() {
		return 322;
	}
}
