package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.ModifyCardCostSpell;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;

public class EmperorThaurissan extends MinionCard {

	public EmperorThaurissan() {
		super("Emperor Thaurissan", 5, 5, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("At the end of your turn, reduce the Cost of cards in your hand by (1).");
	}

	@Override
	public Minion summon() {
		Minion emperorThaurissan = createMinion();
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), ModifyCardCostSpell.create(-1));
		emperorThaurissan.setSpellTrigger(trigger);
		return emperorThaurissan;
	}



	@Override
	public int getTypeId() {
		return 632;
	}
}
