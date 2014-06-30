package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ComboSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Headcrack extends SpellCard {

	public Headcrack() {
		super("Headcrack", Rarity.RARE, HeroClass.ROGUE, 2);
		setDescription("Deal $2 damage to the enemy hero. Combo: Return this to your hand next turn.");

		Spell noComboSpell = new DamageSpell(2);
		noComboSpell.setTarget(EntityReference.ENEMY_HERO);
		Spell comboSpell = new MetaSpell(noComboSpell, new ReceiveCardSpell(this));
		setSpell(new ComboSpell(noComboSpell, comboSpell));

		setTargetRequirement(TargetSelection.NONE);
	}

}
