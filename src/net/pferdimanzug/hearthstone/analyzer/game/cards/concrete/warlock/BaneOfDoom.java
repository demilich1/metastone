package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ConditionalEffectSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class BaneOfDoom extends SpellCard {

	public BaneOfDoom() {
		super("Bane of Doom", Rarity.EPIC, HeroClass.WARLOCK, 5);
		setDescription("Deal 2 damage to a character.  If that kills it, summon a random Demon.");
		
		SpellDesc damage = DamageSpell.create(2);
		SpellDesc summonRandomDemon = SummonRandomSpell.create(
					new BloodImp(),
					new Voidwalker(),
					new FlameImp(),
					new Succubus(),
					new Felguard(),
					new DreadInfernal());
		
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
