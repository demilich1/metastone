package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.KelThuzadSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

public class KelThuzad extends MinionCard {

	public KelThuzad() {
		super("Kel'Thuzad", 6, 8, Rarity.LEGENDARY, HeroClass.ANY, 8);
		setDescription("At the end of the turn, summon all friendly minions that died this turn.");
	}

	@Override
	public int getTypeId() {
		return 414;
	}
	
	@Override
	public Minion summon() {
		Minion kelThuzad = createMinion();
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), KelThuzadSpell.create());
		kelThuzad.setSpellTrigger(trigger);
		return kelThuzad;
	}

	
}
