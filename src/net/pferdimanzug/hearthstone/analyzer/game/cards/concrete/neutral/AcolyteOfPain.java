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
		super("Acolyte of Pain", Rarity.COMMON, HeroClass.ANY, 3);
	}

	@Override
	public Minion summon() {
		Minion acolyteOfPain = createMinion(1, 3);
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(), new DrawCardSpell());
		acolyteOfPain.setSpellTrigger(trigger);
		return acolyteOfPain;
	}

}
