package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class RockbiterWeapon extends SpellCard {

	private static final int ATTACK_BONUS = 3;

	public RockbiterWeapon() {
		super("Rockbiter Weapon", Rarity.FREE, HeroClass.SHAMAN, 1);
		SpellTrigger endBuffTrigger = new SpellTrigger(new TurnEndTrigger(), new BuffSpell(-ATTACK_BONUS));
		Spell buff = new BuffSpell(+ATTACK_BONUS);
		Spell endBuff = new AddSpellTriggerSpell(endBuffTrigger);
		setSpell(new MetaSpell(buff, endBuff));
		setTargetRequirement(TargetSelection.FRIENDLY_CHARACTERS);
	}

}
