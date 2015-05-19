package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ConditionalEffectSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.SummonRandomSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class BaneOfDoom extends SpellCard {

	public BaneOfDoom() {
		super("Bane of Doom", Rarity.EPIC, HeroClass.WARLOCK, 5);
		setDescription("Deal 2 damage to a character.  If that kills it, summon a random Demon.");
		
		SpellDesc damage = DamageSpell.create(2);
		SpellDesc summonRandomDemon = SummonRandomSpell.create(
					//new BloodImp()
					//new Voidwalker(),
					//new FlameImp(),
					//new Succubus()
					//new Felguard(),
					//new DreadInfernal()
					);
		
		SpellDesc baneOfDoom = ConditionalEffectSpell.create(damage, summonRandomDemon,(context, player, entity) -> {
			Actor target = (Actor) entity;
			return target.isDead();
		});
		setSpell(baneOfDoom);
		setTargetRequirement(TargetSelection.ANY);
	}
	
	@Override
	public int getTypeId() {
		return 334;
	}
	
}
