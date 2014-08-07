package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ShadowformSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Shadowform extends SpellCard {

	public Shadowform() {
		super("Shadowform", Rarity.EPIC, HeroClass.PRIEST, 3);
		setDescription("Your Hero Power becomes 'Deal 2 damage'. If already in Shadowform: 3 damage.");
		
		setSpell(ShadowformSpell.create());
		setPredefinedTarget(EntityReference.FRIENDLY_HERO);
		setTargetRequirement(TargetSelection.NONE);
	}
	
	@Override
	public int getTypeId() {
		return 277;
	}
	
	

	
}
