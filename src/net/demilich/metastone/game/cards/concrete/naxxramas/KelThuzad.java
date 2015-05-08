package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.custom.KelThuzadSpell;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;

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
		//SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(TargetPlayer.BOTH), KelThuzadSpell.create());
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(null), KelThuzadSpell.create());
		kelThuzad.setSpellTrigger(trigger);
		return kelThuzad;
	}
	
}
