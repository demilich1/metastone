package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class BaronGeddon extends MinionCard {

	public BaronGeddon() {
		super("Baron Geddon", 7, 5, Rarity.LEGENDARY, HeroClass.ANY, 7);
		setDescription("At the end of your turn, deal 2 damage to ALL other characters.");
	}

	@Override
	public int getTypeId() {
		return 92;
	}

	@Override
	public Minion summon() {
		Minion baronGeddon = createMinion();
		Spell damageSpell = new DamageSpell(2);
		damageSpell.setTarget(EntityReference.ALL_CHARACTERS);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), damageSpell);
		baronGeddon.setSpellTrigger(trigger);
		return baronGeddon;
	}
}
