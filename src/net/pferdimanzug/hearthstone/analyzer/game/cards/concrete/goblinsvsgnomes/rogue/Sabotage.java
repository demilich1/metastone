package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ComboSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroyWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Sabotage extends SpellCard {

	public Sabotage() {
		super("Sabotage", Rarity.EPIC, HeroClass.ROGUE, 4);
		setDescription("Destroy a random enemy minion. Combo: And your opponent's weapon.");
		
		SpellDesc destroyMinionSpell = DestroySpell.create();
		destroyMinionSpell.setTarget(EntityReference.ENEMY_MINIONS);
		destroyMinionSpell.pickRandomTarget(true);
		
		SpellDesc destroyWeaponSpell = DestroyWeaponSpell.create(); 
		destroyWeaponSpell.setTarget(EntityReference.ENEMY_HERO);
		
		SpellDesc comboSpell = MetaSpell.create(destroyMinionSpell, destroyWeaponSpell);
		comboSpell.setTarget(EntityReference.NONE);
		
		setSpell(ComboSpell.create(destroyMinionSpell, comboSpell));
		setTargetRequirement(TargetSelection.NONE);
	}

}
