package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, +1, +1);
		//SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(TargetPlayer.BOTH), buffSpell);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(null), buffSpell);
		gruul.setSpellTrigger(trigger);
		return gruul;
	}
}
