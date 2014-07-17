package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AvengingWrath extends SpellCard {

	public AvengingWrath() {
		super("Avenging Wrath", Rarity.EPIC, HeroClass.PALADIN, 6);
		setDescription("Deal 8 damage randomly split among enemy characters.");
		
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new DamageRandomSpell(1, 8));
		setPredefinedTarget(EntityReference.ENEMY_CHARACTERS);
	}



	@Override
	public int getTypeId() {
		return 236;
	}
}
