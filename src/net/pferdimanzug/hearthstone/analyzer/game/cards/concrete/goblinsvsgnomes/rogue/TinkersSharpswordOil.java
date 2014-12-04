package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ComboSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class TinkersSharpswordOil extends SpellCard {

	public TinkersSharpswordOil() {
		super("Tinker's Sharpsword Oil", Rarity.COMMON, HeroClass.ROGUE, 4);
		setDescription("Give your weapon +3 Attack. Combo: Give a random friendly minion +3 Attack.");
		
		SpellDesc buffWeapon = BuffWeaponSpell.create(+3);
		SpellDesc buffRandomMinion = BuffSpell.create(+3);
		buffRandomMinion.setTarget(EntityReference.FRIENDLY_MINIONS);
		buffRandomMinion.pickRandomTarget(true);
		
		SpellDesc comboSpell = MetaSpell.create(buffWeapon, buffRandomMinion);
		comboSpell.setTarget(EntityReference.NONE);
		
		setSpell(ComboSpell.create(buffWeapon, comboSpell));
		setTargetRequirement(TargetSelection.NONE);
	}

}
