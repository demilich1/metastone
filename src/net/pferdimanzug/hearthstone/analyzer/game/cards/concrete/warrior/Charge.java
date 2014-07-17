package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Charge extends SpellCard {

	public Charge() {
		super("Charge", Rarity.FREE, HeroClass.WARRIOR, 3);
		setDescription("Give a friendly minion +2 Attack and Charge.");
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		Spell spell = new MetaSpell(new BuffSpell(2), new ApplyTagSpell(GameTag.CHARGE));
		setSpell(spell);
	}



	@Override
	public int getTypeId() {
		return 364;
	}
}
