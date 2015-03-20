package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.ShadowformSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Shadowform extends SpellCard {

	public Shadowform() {
		super("Shadowform", Rarity.EPIC, HeroClass.PRIEST, 3);
		setDescription("Your Hero Power becomes 'Deal 2 damage'. If already in Shadowform: 3 damage.");
		
		setSpell(ShadowformSpell.create());
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public int getTypeId() {
		return 277;
	}
	
	

	
}
