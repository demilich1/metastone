package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HolyNova extends SpellCard {
	
	public HolyNova() {
		super("Holy Nova", Rarity.FREE, HeroClass.PRIEST, 5);
		setDescription("Deal $2 damage to all enemies. Restore #2 Health to all friendly characters.");
		Spell damageComponent = new DamageSpell(2);
		damageComponent.setTarget(EntityReference.ENEMY_CHARACTERS);
		Spell healComponent = new HealingSpell(+2);
		healComponent.setTarget(EntityReference.FRIENDLY_CHARACTERS);
		setSpell(new MetaSpell(damageComponent, healComponent));
		setTargetRequirement(TargetSelection.NONE);
	}



	@Override
	public int getTypeId() {
		return 264;
	}
}
