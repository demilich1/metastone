package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class CobraShot extends SpellCard {

	public CobraShot() {
		super("Cobra Shot", Rarity.COMMON, HeroClass.HUNTER, 5);
		setDescription("Deal 3 damage to a minion and the enemy hero.");
		
		SpellDesc damageMinion = DamageSpell.create(3);
		SpellDesc damageHero = DamageSpell.create(3);
		damageHero.setTarget(EntityReference.ENEMY_HERO);
		setSpell(MetaSpell.create(damageMinion, damageHero));
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 485;
	}
}
