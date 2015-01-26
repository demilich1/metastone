package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

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
