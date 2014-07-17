package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.DamageReceivedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class AcolyteOfPain extends MinionCard {

	public AcolyteOfPain() {
		super("Acolyte of Pain", 1, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Whenever this minion takes damage, draw a card.");
	}

	@Override
	public int getTypeId() {
		return 79;
	}



	@Override
	public Minion summon() {
		Minion acolyteOfPain = createMinion();
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(), new DrawCardSpell());
		acolyteOfPain.setSpellTrigger(trigger);
		return acolyteOfPain;
	}
}
