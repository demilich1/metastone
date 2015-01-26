package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HolySmite extends SpellCard {
	
	public static final int DAMAGE = 2;

	public HolySmite() {
		super("Holy Smite", Rarity.FREE, HeroClass.PRIEST, 1);
		setDescription("Deal $2 damage.");
		setTargetRequirement(TargetSelection.ANY);
		setSpell(DamageSpell.create(DAMAGE));
	}



	@Override
	public int getTypeId() {
		return 265;
	}
}
