package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Gruul extends MinionCard {

	public Gruul() {
		super("Gruul", 7, 7, Rarity.LEGENDARY, HeroClass.ANY, 8);
		setDescription("At the end of each turn, gain +1/+1 .");
	}

	@Override
	public int getTypeId() {
		return 137;
	}



	@Override
	public Minion summon() {
		Minion gruul = createMinion();
		Spell buffSpell = new BuffSpell(+1, +1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(TargetPlayer.BOTH), buffSpell);
		gruul.setSpellTrigger(trigger);
		return gruul;
	}
}
