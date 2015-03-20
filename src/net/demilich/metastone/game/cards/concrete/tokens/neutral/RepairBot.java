package net.demilich.metastone.game.cards.concrete.tokens.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class RepairBot extends MinionCard {

	public RepairBot() {
		super("Repair Bot", 0, 3, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("At the end of your turn, restore 6 Health to a damaged character.");
		setRace(Race.MECH);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 449;
	}

	@Override
	public Minion summon() {
		Minion repairBot = createMinion();
		SpellDesc healRandomSpell = HealingSpell.create(EntityReference.ALL_CHARACTERS, 6, true);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), healRandomSpell);
		repairBot.setSpellTrigger(trigger);
		return repairBot;
	}
}
