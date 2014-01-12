package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HolyNova extends SpellCard {
	
	public HolyNova() {
		super("Holy Nova", Rarity.FREE, HeroClass.PRIEST, 5);
		Spell damageComponent = new DamageSpell(2);
		damageComponent.setTarget(TargetKey.ENEMY_CHARACTERS);
		Spell healComponent = new HealingSpell(+2);
		healComponent.setTarget(TargetKey.FRIENDLY_CHARACTERS);
		setSpell(new MetaSpell(damageComponent, healComponent));
		setTargetRequirement(TargetSelection.NONE);
	}

}
